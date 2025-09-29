# 2. 导入FastAPI
import os
import json

from fastapi import FastAPI, File, UploadFile, Query, Body
from starlette.middleware.cors import CORSMiddleware
from fastapi.responses import StreamingResponse, FileResponse, JSONResponse

# 3. 导入ollama 模型
from langchain_ollama import OllamaLLM
from langchain.chains import RetrievalQA

from common import load_documents, create_vector_store, create_llm, K_INDEX, CONTRACTS_DIR

# 用于存储当前选择使用的文件列表
selected_files = []

# 文档内容列表
knowledges = []

# 向量数据库
vector_store = None

# 加载deepseek 模型
llm = create_llm(streaming=True)

app = FastAPI(title='RAG The Stream knowledge base')
"""
CORS 跨域资源共享
"""
app.add_middleware(
    CORSMiddleware,
    allow_origins=['*'],
    allow_credentials=True,
    allow_methods=['*'],
    allow_headers=['*'],
)

# 提供静态文件服务
@app.get("/static/{file_path:path}")
async def serve_static(file_path: str):
    return FileResponse(f"static/{file_path}")

@app.get("/")
async def serve_index():
    return FileResponse("static/index.html")

# 接收前端输入的接口
@app.post('/chat')
def chat(query: str = Body(..., embed=True)):
    # 只有当用户选择了文件时才使用向量数据库检索
    if selected_files and vector_store:
        docs = vector_store.similarity_search(query, k=K_INDEX)
        context_text = '\n'.join([doc.page_content for doc in docs])
        prompt = f'{context_text}\nQuestion: {query}\nAnswer:'
        print('读取了向量库')
    else:
        prompt = f'Question: {query}\nAnswer:'
        print('没有读取向量库')

    def token_stream():
        for token in llm.stream(prompt):
            # 逐字输出
            yield token

    return StreamingResponse(token_stream(), media_type='text/event-stream')

# 文件上传接口
@app.post('/upload')
async def upload_file(file: UploadFile = File(...)):
    global knowledges, vector_store
    
    # 验证文件类型
    if not (file.filename.endswith('.txt') or file.filename.endswith('.md')):
        return JSONResponse(status_code=400, content={'error': '只支持.txt和.md格式的文件'})
    
    # 验证文件大小（限制为10MB）
    file_content = await file.read()
    if len(file_content) > 10 * 1024 * 1024:
        return JSONResponse(status_code=400, content={'error': '文件大小不能超过10MB'})
    
    try:
        # 确保text_directory目录存在
        os.makedirs(CONTRACTS_DIR, exist_ok=True)
        
        # 保存文件
        file_path = os.path.join(CONTRACTS_DIR, file.filename)
        with open(file_path, 'wb') as f:
            f.write(file_content)
        
        # 将新上传的文件添加到selected_files中
        if file.filename not in selected_files:
            selected_files.append(file.filename)
        
        # 加载选择的文件并创建向量数据库
        knowledges = load_documents(selected_files)
        if knowledges:
            vector_store = create_vector_store(knowledges)
        
        return JSONResponse(content={'success': True, 'message': '文件上传成功'})
    except Exception as e:
        return JSONResponse(status_code=500, content={'error': str(e)})

# 获取已上传文件列表的接口
@app.get('/files')
def get_files():
    try:
        files = []
        if os.path.exists(CONTRACTS_DIR):
            for filename in os.listdir(CONTRACTS_DIR):
                if filename.endswith('.txt') or filename.endswith('.md'):
                    file_path = os.path.join(CONTRACTS_DIR, filename)
                    file_size = os.path.getsize(file_path) / 1024  # KB
                    file_time = os.path.getmtime(file_path)
                    files.append({
                        'name': filename,
                        'size': f"{file_size:.2f} KB",
                        'time': file_time,
                        'selected': filename in selected_files
                    })
            # 按修改时间排序，最新的在前
            files.sort(key=lambda x: x['time'], reverse=True)
        return JSONResponse(content={'files': files})
    except Exception as e:
        return JSONResponse(status_code=500, content={'error': str(e)})

# 清空知识库的接口
@app.post('/clear-knowledge')
def clear_knowledge():
    global knowledges, vector_store, selected_files
    try:
        # 清空选择的文件列表
        selected_files = []
        
        # 检查目录是否存在
        if os.path.exists(CONTRACTS_DIR):
            # 删除目录中的所有.txt和.md文件
            for filename in os.listdir(CONTRACTS_DIR):
                if filename.endswith('.txt') or filename.endswith('.md'):
                    file_path = os.path.join(CONTRACTS_DIR, filename)
                    os.remove(file_path)
        
        # 重新初始化文档和向量数据库
        knowledges = []
        vector_store = None
        
        return JSONResponse(content={'success': True, 'message': '知识库已清空'})
    except Exception as e:
        return JSONResponse(status_code=500, content={'error': str(e)})

# 选择文件的接口 - 支持选择文件和排除单个文件
@app.post('/select-files')
async def select_files(request_body: dict = Body(None)):
    global selected_files, knowledges, vector_store
    try:
        # 确保request_body存在
        if not request_body:
            request_body = {}
            
        # 检查是否有排除文件的请求
        exclude_file = request_body.get('exclude')
        file_names = request_body.get('file_names')
        
        if exclude_file:
            # 如果要排除单个文件，从selected_files中移除它
            if exclude_file in selected_files:
                selected_files.remove(exclude_file)
        elif file_names:
            # 正常的文件选择
            selected_files = file_names.split(',') if file_names else []
        else:
            # 清空选择
            selected_files = []
            
        # 加载选择的文件
        knowledges = load_documents(selected_files)
        
        # 更新向量数据库
        if knowledges:
            vector_store = create_vector_store(knowledges)
        else:
            vector_store = None
        
        if selected_files:
            return JSONResponse(content={'success': True, 'message': f'已成功选择 {len(selected_files)} 个文件作为知识库'})
        else:
            return JSONResponse(content={'success': True, 'message': '未找到有效的文件'})
    except Exception as e:
        return JSONResponse(status_code=500, content={'error': str(e)})

# 删除单个文件的接口
@app.post('/delete-file')
def delete_file(filename: str = Query(None)):
    global knowledges, vector_store, selected_files
    try:
        if not filename:
            return JSONResponse(status_code=400, content={'error': '文件名不能为空'})
        
        file_path = os.path.join(CONTRACTS_DIR, filename)
        if not os.path.exists(file_path):
            return JSONResponse(status_code=404, content={'error': '文件不存在'})
        
        # 检查文件类型
        if not (filename.endswith('.txt') or filename.endswith('.md')):
            return JSONResponse(status_code=400, content={'error': '只能删除.txt和.md格式的文件'})
        
        # 删除文件
        os.remove(file_path)
        
        # 如果该文件在已选择列表中，移除它
        if filename in selected_files:
            selected_files.remove(filename)
        
        # 加载选择的文件
        knowledges = load_documents(selected_files)
        
        # 更新向量数据库
        if knowledges:
            vector_store = create_vector_store(knowledges)
        else:
            vector_store = None
        
        return JSONResponse(content={'success': True, 'message': f'文件 "{filename}" 已成功删除'})
    except Exception as e:
        return JSONResponse(status_code=500, content={'error': str(e)})

# 批量删除文件的接口
@app.post('/batch-delete-files')
def batch_delete_files(file_names: str = Query(None)):
    global knowledges, vector_store, selected_files
    try:
        if not file_names:
            return JSONResponse(status_code=400, content={'error': '文件名列表不能为空'})
        
        filenames = file_names.split(',')
        deleted_count = 0
        
        for filename in filenames:
            file_path = os.path.join(CONTRACTS_DIR, filename)
            if os.path.exists(file_path) and (filename.endswith('.txt') or filename.endswith('.md')):
                os.remove(file_path)
                deleted_count += 1
                
                # 如果该文件在已选择列表中，移除它
                if filename in selected_files:
                    selected_files.remove(filename)
        
        # 加载选择的文件
        knowledges = load_documents(selected_files)
        
        # 更新向量数据库
        if knowledges:
            vector_store = create_vector_store(knowledges)
        else:
            vector_store = None
        
        return JSONResponse(content={'success': True, 'message': f'已成功删除 {deleted_count} 个文件'})
    except Exception as e:
        return JSONResponse(status_code=500, content={'error': str(e)})

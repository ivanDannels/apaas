import os
from langchain_ollama import OllamaLLM, OllamaEmbeddings
from langchain_community.vectorstores import FAISS

# 配置存放知识库的目录
CONTRACTS_DIR = 'text_directory'

# 设置每次向量数据库检索获取的前3条
K_INDEX = 3

def load_all_documents():
    """加载所有txt和md文件内容"""
    return load_documents(os.listdir(CONTRACTS_DIR))

def load_documents(file_names=None):
    """加载指定文件或目录中的所有txt和md文件内容
    
    Args:
        file_names: 可选，要加载的文件名列表。如果为None，则不加载任何文件。
    
    Returns:
        list: 包含文件内容的列表
    """
    knowledges = []
    
    # 只有当提供了文件名列表时才加载文件
    if file_names:
        for filename in file_names:
            # 判断文件类型, 只处理txt和md文件
            if filename.endswith('.txt') or filename.endswith('.md'):
                file_path = os.path.join(CONTRACTS_DIR, filename)
                # 检查文件是否存在
                if os.path.exists(file_path):
                    # 读取文件内容, 以UTF-8 编码读取文件内容
                    with open(file_path, 'r', encoding='utf-8') as f:
                        text = f.read()
                        knowledges.append(text)
    
    return knowledges

def create_vector_store(knowledges):
    """创建向量数据库"""
    # 将读取文档中的内容转换为向量 - 嵌入模型
    embedding_model = OllamaEmbeddings(model='bge-m3:567m')
    
    # 创建向量数据库,将内容存储为向量
    vector_store = FAISS.from_texts(knowledges, embedding_model)
    return vector_store

def create_llm(streaming=False):
    """创建LLM模型"""
    llm = OllamaLLM(
        model='deepseek-r1:1.5b',
        streaming=streaming,
    )
    return llm
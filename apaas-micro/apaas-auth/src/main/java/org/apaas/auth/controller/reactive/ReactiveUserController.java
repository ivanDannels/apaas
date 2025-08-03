package org.apaas.auth.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.auth.domain.dto.UserDTO;
import org.apaas.core.query.PageResult;
import org.apaas.auth.entity.User;
import org.apaas.auth.service.reactive.ReactiveUserService;
import org.apaas.core.web.controller.ReactiveBaseController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式用户控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/users")
@Tag(name = "响应式用户管理", description = "响应式用户相关操作")
public class ReactiveUserController extends ReactiveBaseController<User, Long, ReactiveUserService> {

    public ReactiveUserController(ReactiveUserService service) {
        super(service);
    }

    /**
     * 用户登录
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "用户登录", description = "用户登录获取token")
    @Parameters({
        @Parameter(name = "username", description = "用户名", required = true),
        @Parameter(name = "password", description = "密码", required = true)
    })
    public Mono<String> login(@RequestParam String username, @RequestParam String password) {
        return service.login(username, password)
                .switchIfEmpty(Mono.error(new RuntimeException("用户名或密码错误")));
    }

    /**
     * 用户注册
     */
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "用户注册", description = "创建新用户")
    public Mono<Boolean> register(@RequestBody User user) {
        return service.register(user);
    }

    /**
     * 分页查询用户
     */
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询用户", description = "根据条件分页查询用户列表")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "username", description = "用户名，模糊查询"),
        @Parameter(name = "nickname", description = "昵称，模糊查询"),
        @Parameter(name = "phone", description = "手机号码，模糊查询"),
        @Parameter(name = "status", description = "状态：0-启用，1-禁用"),
        @Parameter(name = "deptId", description = "部门ID")
    })
    public Mono<PageResult<User>> selectPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long deptId) {
        UserDTO query = new UserDTO();
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        query.setUsername(username);
        query.setNickname(nickname);
        query.setPhone(phone);
        query.setStatus(status);
        query.setDeptId(deptId);
        return service.selectPage(query);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户信息")
    public Mono<User> getCurrentUser() {
        return service.getCurrentUser();
    }

    /**
     * 修改密码
     */
    @PutMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改密码", description = "修改当前用户密码")
    @Parameters({
        @Parameter(name = "oldPassword", description = "旧密码", required = true),
        @Parameter(name = "newPassword", description = "新密码", required = true)
    })
    public Mono<Boolean> updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return service.updatePassword(oldPassword, newPassword);
    }
}
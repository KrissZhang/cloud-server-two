package com.self.cloudserver.api.req;

import com.self.cloudserver.constants.Insert;
import com.self.cloudserver.constants.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TestReq {

    @NotNull(groups = {Update.class}, message = "id不能为空")
    private Long id;

    @NotBlank(groups = {Insert.class, Update.class}, message = "名称不能为空")
    private String name;

    @Future(groups = {Insert.class, Update.class}, message = "时间只能晚于当前时间")
    private Date date;

    @Email(groups = {Insert.class, Update.class}, message = "只能是邮箱格式")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

<#import "parts/common.ftlh" as c>

<@c.page>
    <h4 class="text-center">Редактор пользователя:</h4>
    <div class="text-center">
        <form action="/user" method="post">
            <input type="text" name="username" value="${user.username}" />
            <#list roles as role>
                <div>
                    <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </#list>
            <input type="hidden" value="${user.id}" name="userId" />
            <input type="hidden" value="${_csrf.token}" name="_csrf" />
            <button type="submit">Сохранить</button>
        </form>
    </div>
</@c.page>
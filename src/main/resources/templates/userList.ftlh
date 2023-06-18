<#import "parts/common.ftlh" as c>

<@c.page>
    <h4 class="text-center">Список пользователей</h4>
    <table class="table table-striped" style="table-border-color-dark: #222222;">
        <thead>
        <tr>
            <th scope="col" style="font-size: 16px">#</th>
            <th scope="col" style="font-size: 16px">Логин</th>
            <th scope="col" style="font-size: 16px">Роли</th>
            <th scope="col" style="font-size: 16px">Действие</th>
        </tr>
        </thead>

        <tbody>
            <#list users as user>
                <tr>
                    <th scope="row" style="font-size: 16px">${user.getId()}</th>
                    <td style="font-size: 16px">${user.username}</td>
                    <td style="font-size: 16px"><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td style="font-size: 14px"><a href="/user/${user.id}" class="btn btn-primary">Редактировать</a></td>
                </tr>
            </#list>
        </tbody>
    </table>
</@c.page>
<#import "parts/common.ftlh" as c>

<@c.page>
        <h4 class="text-center">Список пользователей</h4>
        <table class="mx-auto">
            <thead>
            <tr>
                <th>Name</th>
                <th>Role</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">редактировать</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
</@c.page>
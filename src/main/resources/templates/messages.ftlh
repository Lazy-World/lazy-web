<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>

<@c.page>
    <div class="col cols-md-2" id="user-list">
        <#list users as user>
            <div class="col my-3" data-id="${user.getId()}">
                <#if user.getId() == currentId>
                    <a class="nav-link" href="?sel=${user.getId()}">Избранное</a>
                <#else>
                    <a class="nav-link" href="?sel=${user.getId()}">${user.getUsername()}</a>
                </#if>
            </div>
        </#list>

        <div class="row row-cols-md-1" id="messages-history">
            <#if messages??>
                <#if s.isUser>
                    <div class="text-center">
                        <a class="btn btn-light my-2" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                            Написать сообщение
                        </a>
                    </div>
                    <div class="collapse" id="collapseExample">
                        <form method="post" enctype="multipart/form-data" class="col-md-3 mx-auto needs-validation">
                            <div class="col mb-1">
                                <textarea type="text" name="text" class="form-control ${((errorMap['textError'])??)?string('is-invalid', '')}" value="<#if myMessage??>${myMessage.text}</#if>" placeholder="Текст сообщения"></textarea>
                                <#if (errorMap['textError'])??>
                                    <div class="invalid-feedback">
                                        ${(errorMap['textError'])!''}
                                    </div>
                                </#if>
                            </div>

                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <div class="mb-2">
                                <button class="btn btn-success" type="submit">Отправить</button>
                            </div>
                        </form>
                    </div>
                </#if>

                <#list messages as message>
                    <div class="col" data-id="${message.getId()}">
                        <div class="card mx-auto mb-3" style="max-width: 540px;">
                            <div class="row g-0">
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <p class="card-text">${message.getText()}</p>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted"><b>${message.getMessageDateTimeString()}</b> Написал: <b>${message.getAuthorName()}</b></small>
                                    <button type="button" class="btn btn-outline-secondary">Редактировать</button>
                                </div>
                            </div>
                        </div>
                    </div>
                <#else>
                    <div class="text-center">Список сообщений пуст</div>
                </#list>
            <#else>
                <div class="text-center">нет открытых чатов</div>
            </#if>
        </div>
    </div>
</@c.page>
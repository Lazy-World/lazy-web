<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>

<@c.page>
    <div class="row">
        <div class="col-md-3" style="position: static">
            <span class="navbar-brand mb-0 h1">Пользователи:</span>
            <nav class="navbar navbar-expand-md bg-body-tertiary">
                <div class="container-fluid">
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#user-list" aria-controls="user-list" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="user-list">
                        <ul class="navbar-nav flex-column me-auto mb-2 mb-lg-0">
                            <#list users as user>
                                <li class="nav-item">
                                    <#if user.getId() == currentId>
                                        <a class="nav-link" href="?sel=${user.getId()}">Избранное</a>
                                    <#else>
                                        <a class="nav-link" href="?sel=${user.getId()}">${user.getUsername()}</a>
                                    </#if>
                                </li>
                            </#list>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>

        <div class="col cols-md-2" id="messages-history">
            <#if messages??>
                <div id="messages-container" class="custom-scrollbar mt-4 rounded-3">
                    <div class="pinned-top" style="background-color: #222222;">
                        <div class="d-flex justify-content-center">
                            <div class="d-block flex-grow-1 text-center">
                                <h5 class="mb-0 mt-2">
                                    <#if secondUser.getId() == currentId>
                                        Избранное
                                    <#else>
                                        ${secondUser.getUsername()}
                                    </#if>
                                </h5>
                            </div>
                        </div>
                        <hr class="hr-sep">
                    </div>

                    <#assign currentMessages = messages?filter(m -> m.getAuthor().getId() == currentId)>
                    <#assign otherMessages = messages?filter(m -> m.getAuthor().getId() != currentId)>

                    <#assign prevDate = "">
                    <#assign nextTimeSelf = "">
                    <#assign nextTime = "">
                    <#assign i = 1>
                    <#assign j = 1>

                    <#list messages as message>
                        <#assign isCurrentAuthor = (message.getAuthor().getId() == currentId)>

                        <#if message.getMessageDateString() != prevDate>
                            <div class="text-center mb-2">${message.getMessageDateString()}</div>
                        </#if>
                        <#assign prevDate = message.getMessageDateString()>

                        <#if messages[message?index + 1]??>
                            <#if currentMessages[i]??>
                                <#assign nextTimeSelf = currentMessages[i].getMessageTimeString()>
                            <#else>
                                <#assign nextTimeSelf = "">
                            </#if>

                            <#if otherMessages[j]??>
                                <#assign nextTime = otherMessages[j].getMessageTimeString()>
                            <#else>
                                <#assign nextTime = "">
                            </#if>
                        <#else>
                            <#assign nextTimeSelf = "">
                            <#assign nextTime = "">
                        </#if>

                        <div class="col" data-id="${message.getId()}">
                            <div class="${isCurrentAuthor?string('d-flex justify-content-end text-end mb-10 me-3', 'flex-grow-1 ms-3')}">
                                <div class="w-100">
                                    <div class="d-flex flex-column ${isCurrentAuthor?string('align-items-end', 'align-items-start')}">
                                        <div class="${isCurrentAuthor?string('bg-primary text-white', 'bg-light text-secondary')} p-2 px-3 rounded-2 mb-1" style="white-space: pre-line;">${message.getText()}</div>
                                        <#if isCurrentAuthor>
                                            <#if message.getMessageTimeString() != nextTimeSelf>
                                                <div class="small mb-2">
                                                    <div class="small">${message.getMessageTimeString()}</div>
                                                    <div class="small"><i class="fa-solid fa-check"></i></div>
                                                </div>
                                            </#if>
                                            <#assign i = i + 1>
                                        <#else>
                                            <#if message.getMessageTimeString() != nextTime>
                                                <div class="small mb-2">
                                                    <div class="small">${message.getMessageTimeString()}</div>
                                                </div>
                                            </#if>
                                            <#assign j = j + 1>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <#else>
                        <div class="text-center">Список сообщений пуст</div>
                    </#list>
                </div>

                <#if s.isUser>
                    <div class="pinned-bottom my-1 rounded-3 sticky-top" style="background-color: #292929; border: 2px solid #424242;">
                        <form method="post" enctype="multipart/form-data" class="ms-1 me-1 mx-auto needs-validation">
                            <div class="col my-1 d-flex justify-content-between w-100">
                                    <textarea id="my-textarea" name="text" class="form-control mb-0 me-2 ${((errorMap['textError'])??)?string('is-invalid', '')}"
                                              minlength="1" maxlength="2000" value="<#if myMessage??>${myMessage.text}</#if>" data-autoresize placeholder="Cообщение" rows="1"></textarea>
                                <button class="btn btn-success mb-1" type="submit">Отправить</button>
                                <#if (errorMap['textError'])??>
                                    <div class="invalid-feedback">
                                        ${(errorMap['textError'])!''}
                                    </div>
                                </#if>
                            </div>
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        </form>
                    </div>
                </#if>
            <#else>
                <div class="text-center">Нет открытых чатов</div>
            </#if>
        </div>
    </div>

    <script>
        const customScrollbar = document.querySelector(".custom-scrollbar");
        customScrollbar.scrollTop = customScrollbar.scrollHeight;
    </script>
</@c.page>
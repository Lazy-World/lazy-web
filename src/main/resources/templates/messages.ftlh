<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>

<@c.page>
    <div class="row">
        <div class="col col-md-4" id="user-history">
            <div id="user-container" class="user-container mt-4 rounded-3">
                <div class="pinned-top" style="background-color: #222222;">
                    <div class="d-flex justify-content-center">
                        <div class="input-group ms-3 me-3 mt-2 mb-2">
                            <span class="input-group-text" id="basic-addon1">
                                <svg fill="none" height="16" viewBox="0 0 16 16" width="16" xmlns="http://www.w3.org/2000/svg"><path d="M6.5 1a5.5 5.5 0 0 1 4.38 8.82l3.9 3.9a.75.75 0 0 1-1.06 1.06l-3.9-3.9A5.5 5.5 0 1 1 6.5 1zm0 1.5a4 4 0 1 0 0 8 4 4 0 0 0 0-8z" fill="currentColor"></path></svg>
                            </span>

                            <input id="search-input" type="search" class="form-control" placeholder="Поиск" aria-label="Search" aria-describedby="basic-addon1"></input>
                        </div>
                    </div>
                    <hr class="hr-sep mt-0"></hr>
                </div>

                <#list users as user>
                    <div class="user">
                        <a href="?sel=${user.getId()}">
                            <div class="avatar">
                                <img src="https://via.placeholder.com/50" alt="png"></img>
                            </div>
                            <div class="user-info">
                                <h5 class="user-name">${(user.getId() == currentId)?string('Избранное', user.getUsername())}</h5>
                                <p class="last-message">Скоро добавим...</p>
                            </div>
                        </a>
                    </div>
                </#list>
            </div>
        </div>

        <div class="col col-md-8 message-col" id="messages-history">
            <div id="messages-container" class="message-container mt-4 rounded-3">
                <#if messages??>
                    <div class="pinned-top" style="background-color: #222222;">
                        <div class="d-flex justify-content-center">
                            <div class="d-block flex-grow-1 text-center">
                                <button id="back-to-user-list-btn" type="button" class="btn btn-primary back-btn ms-2">Назад</button>
                                <h5 class="mb-0 mt-2 centered-text">${(secondUser.getId() == currentId)?string('Избранное', secondUser.getUsername())}</h5>
                            </div>
                        </div>
                        <hr class="hr-sep" />
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
                                        <div class="${isCurrentAuthor?string('bg-primary text-white', 'bg-light text-secondary')} p-2 px-3 rounded-2 mb-1" style="white-space: pre-line; font-size: 14px">${message.getText()}</div>
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
                <#else>
                    <h4 style="display: flex; align-items: center; justify-content: center; height: 100%;">Нет открытых чатов</h4>
                </#if>
            </div>

            <#if messages??>
                <#if s.isUser>
                    <div class="pinned-bottom my-1 rounded-3 sticky-top" style="background-color: #292929; border: 2px solid #424242;">
                        <form method="post" enctype="multipart/form-data" class="ms-1 me-1 mx-auto needs-validation">
                            <div class="col my-1 d-flex justify-content-between w-100">
                                <label for="my-textarea"></label><textarea id="my-textarea" name="text" class="form-control mb-0 me-2 ${((errorMap['textError'])??)?string('is-invalid', '')}"
                                                                           minlength="1" maxlength="2000" data-autoresize="data-autoresize" placeholder="Cообщение" rows="1"></textarea>
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
            </#if>
        </div>
    </div>

    <script>
        const customScrollbar = document.querySelector(".message-container");
        customScrollbar.scrollTop = customScrollbar.scrollHeight;
    </script>

    <script>
        const searchInput = document.querySelector('#search-input');
        const userList = document.querySelector('#user-container');

        searchInput.addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            const users = userList.querySelectorAll('.user');
            users.forEach(function(user) {
                const userName = user.querySelector('.user-name').textContent.toLowerCase();
                if (userName.includes(searchTerm)) {
                    user.style.display = 'block';
                } else {
                    user.style.display = 'none';
                }
            });
        });
    </script>
    <script>
        const urlParams = new URLSearchParams(window.location.search);
        const sel = urlParams.get('sel');

        if (sel) {
            document.body.classList.add('chat-selected');
            localStorage.setItem('chatSelected', 'true');
        } else {
            localStorage.setItem('chatSelected', 'false');
        }

        document.querySelector('.back-btn').addEventListener('click', function() {
            document.body.classList.remove('chat-selected');
            localStorage.setItem('chatSelected', 'false');
        });
    </script>
</@c.page>
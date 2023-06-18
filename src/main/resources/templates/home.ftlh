<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>
<#import "parts/updateLogForm.ftlh" as updLog>

<@c.page>
    <div class="text-center">
        <h2>LazyHub</h2>
    </div>

    <div class="divider-custom divider-light">
        <div class="divider-custom-line"></div>
    </div>

    <div class="text-center mb-4">
        <h5>Инструмент, разработанный для автоматизации процесса загрузки файлов с репозиториев на GitHub и настройки конфигурационных файлов из приложения. С его помощью процесс установки и настройки необходимых файлов значительно упрощается.</h5>
    </div>

    <div id="carouselExampleIndicators" class="carousel carousel-fade carousel-dark slide" data-bs-touch="true">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="3" aria-label="Slide 4"></button>
    </div>
    <div class="carousel-inner mb-3">
        <div class="carousel-item active">
            <img src="../static/preview_1.png" class="d-block w-100 carousel-image" alt="картинка_1" />
        </div>
        <div class="carousel-item">
            <img src="../static/preview_2.png" class="d-block w-100 carousel-image" alt="картинка_2" />
        </div>
        <div class="carousel-item">
            <img src="../static/preview_3.png" class="d-block w-100 carousel-image" alt="картинка_3" />
        </div>
        <div class="carousel-item">
            <img src="../static/preview_4.png" class="d-block w-100 carousel-image" alt="картинка_4" />
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
    </div>

    <div style="font-size: 16px; padding: 10px 20px 20px;">
        <h5>Возможности</h5>
        <ol>
            <li style="margin-bottom: 0.3em"><b style="color: #61ad61">Загрузка файлов с GitHub:</b> LazyHub связан с репозиторием на GitHub и позволяет загрузить выбранные пользователем файлы вместе с их конфигурационными файлами.</li>
            <li style="margin-bottom: 0.3em"><b style="color: #61ad61">Простая настройка конфигурационных файлов:</b> После загрузки файлов с GitHub, LazyHub позволяет пользователю настраивать конфигурационные файлы не выходя из приложения. Программа анализирует содержимое загруженных файлов и предоставляет пользователю возможность настраивать параметры и опции в удобном графическом интерфейсе.</li>
            <li style="margin-bottom: 0.3em"><b style="color: #61ad61">Обновления скриптов:</b> LazyHub способен отслеживать обновления скриптов. В интерфейсе скрипт могут быть подсвечены тремя цветами. Чёрный - скрипт не установлен. Желтый - вышла более новая версия и скрипт требует обновления. Зеленый - последняя версия скрипта установлена.</li>
            <li style="margin-bottom: 0.3em"><b style="color: #61ad61">Обновление библиотек:</b> Так как все скрипты требуют установить какую-либо библиотеку в разделе с доступными для загрузки скриптами можно скачать / обновить данные библиотеки.</li>
            <li style="margin-bottom: 0.3em"><b style="color: #61ad61">Удобное переназначение клавиш:</b> Для того, чтобы не прописывать каждому скрипту список клавиш, которые он должен иметь в своём арсенале в LazyHub'e предусмотрена вкладка со списком всех клавиш, которые могут потребовать скрипты.</li>
        </ol>

        <div class="divider-custom divider-light">
            <div class="divider-custom-line"></div>
        </div>

        <div id="login_message">
            <div class="msg info_msg" style="background-color: #222222;">
                <div class="msg_text" style="font-size: 13px;">
                    <p style="margin-bottom: 0.5em">Ссылка на загрузку: <a href="https://github.com/Lazy-World/warframe-ahk/blob/LazyHub/LazyHub/LazyHubSetup.exe">https://github.com/Lazy-World/LazyHubSetup.exe</a></p>
                    <p style="margin-bottom: 0">Исходный код: <a href="https://github.com/Lazy-World/warframe-loader">https://github.com/Lazy-World/warframe-loader</a></p>
                </div>
            </div>
        </div>
    </div>

    <div class="divider-custom divider-light" style="margin-top: 0">
        <div class="divider-custom-line"></div>
        <div class="divider-custom-icon">
            <svg class="svg-inline--fa fa-dragon fa-w-20" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z" />
                <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z" />
            </svg>
        </div>
        <div class="divider-custom-line"></div>
    </div>

    <#if s.isAdmin>
        <@updLog.updForm false/>
    </#if>

    <div class="row row-cols-1 row-cols-md-3 g-4 mt-0" id="update-log-list">
        <#list updateLogs as updateLog>
            <div class="col" data-id="${updateLog.getId()}">
                <div class="card mx-auto mb-4" style="width: 20rem;">
                    <div class="card-body rounded-3">
                        <h5 class="card-title">${updateLog.getTitle()}</h5>
                        <p class="card-text" style="font-size: 14px">${updateLog.getText()}</p>
                        <#if s.isAdmin>
                            <a href="/edit?log=${updateLog.getId()}" class="btn btn-primary">Редактировать</a>
                        </#if>
                    </div>
                </div>
            </div>
        <#else>
            <div class="text-center mt-3">Новостей пока нет</div>
        </#list>
    </div>

    <script>
        const textInput = document.querySelector('#my-textarea');
        const maxRows = 10;

        textInput.addEventListener('input', function() {
            const lines = this.value.split('\n');
            if (lines.length > maxRows) {
                this.rows = maxRows;
                this.style.overflowY = 'scroll';
            } else {
                this.rows = lines.length;
                this.style.overflowY = 'hidden';
            }
        });
    </script>
</@c.page>
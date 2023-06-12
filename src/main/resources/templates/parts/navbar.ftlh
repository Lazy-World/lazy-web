<#include "security.ftlh">

<nav id='navbar_top' class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Lazy</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div class="navbar-nav me-auto mb-2 mb-sm-0">
                <a class="nav-link" href="/messages">Сообщения</a>
                <#if isAdmin>
                    <a class="nav-link" href="/user">Список пользователей</a>
                </#if>
            </div>

            <form method="get" action="/" class="d-flex" role="search">
                <input type="text" class="form-control me-2 my-2" name="filter" value="${filter!}" aria-label="Search" placeholder="Заголовок новости" />
                <button class="btn btn-outline-success me-3 my-2" type="submit">Поиск</button>
            </form>

            <#if isUser>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <button class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">${name}</button>
                        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-lg-end">
                            <li><a class="dropdown-item" href="/user/profile">Профиль</a></li>
                            <li><hr class="dropdown-divider" /></li>
                            <li>
                                <form action="/logout" method="post">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                    <button type="submit" class="dropdown-item">Выход</button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            <#else>
                <div class="navbar-text me-3">${name}</div>
            </#if>
        </div>
    </div>
</nav>
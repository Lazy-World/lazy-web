<#include "security.ftlh">
<#import "auth.ftlh" as l>

<nav class="navbar navbar-expand-md bg-body-tertiary">
    <div class="container-fluid">
        <span class="navbar-brand mb-0 h1">Lazy</span>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div class="navbar-nav me-auto mb-2 mb-lg-0">
                <a class="nav-link active" aria-current="page" href="/">Home</a>
                <a class="nav-link" href="/messages">Messages</a>
                <#if isAdmin>
                    <a class="nav-link" href="/user">User List</a>
                </#if>
            </div>

            <form method="get" action="/" class="d-flex" role="search">
                <input type="text" class="form-control me-2" name="filter" value="${filter!}" aria-label="Search" placeholder="Search">
                <button class="btn btn-outline-success me-3" type="submit">Search</button>
            </form>

            <#if isUser>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <button class="btn btn-dark dropdown-toggle" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
                            ${name}
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end dropdown-menu-lg-end">
                            <li><a class="dropdown-item" href="/user/profile">Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <form action="/logout" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <li><button type="submit" class="dropdown-item">Sign out</button></li>
                            </form>
                        </ul>
                    </li>
                </ul>
            <#else>
                <div class="navbar-text me-3">${name}</div>
            </#if>
        </div>
    </div>
</nav>
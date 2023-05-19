<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>
<#import "parts/updateLogForm.ftlh" as updLog>

<@c.page>
<#if s.isAdmin>
    <@updLog.updForm false/>
</#if>

<div class="row row-cols-md-1" id="update-log-list">
    <#list updateLogs as updateLog>
        <div class="col" data-id="${updateLog.getId()}">
            <div class="card mx-auto mb-3" style="max-width: 540px;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="/img/${updateLog.getFilename()}" class="img-fluid rounded-start" alt="картинка" />
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${updateLog.getTitle()}</h5>
                            <p class="card-text">${updateLog.getText()}</p>
                        </div>
                    </div>
                    <div class="card-footer">
                        <small class="text-muted">Автор: <b>${updateLog.authorName}</b></small>
                        <#if s.isAdmin>
                            <a class="btn btn-outline-secondary"  href="/edit?log=${updateLog.getId()}">Редактировать</a>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div class="text-center">Новостей пока нет</div>
    </#list>
</div>
</@c.page>
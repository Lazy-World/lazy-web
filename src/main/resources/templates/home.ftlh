<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>
<#import "parts/updateLogForm.ftlh" as updLog>

<@c.page>
<#if s.isAdmin>
    <@updLog.updForm false/>
</#if>

<div class="row row-cols-md-1">
    <#list updateLogs as updateLog>
        <div class="col">
            <div class="card mx-auto mb-3" style="max-width: 540px;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="/img/${updateLog.getFilename()}" class="img-fluid rounded-start" alt="script preview">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${updateLog.getTag()}</h5>
                            <p class="card-text">${updateLog.getText()}</p>
                        </div>
                    </div>
                    <div class="card-footer">
                        <small class="text-muted">Posted by <b>${updateLog.authorName}</b></small>
                        <a class="btn btn-outline-secondary"  href="/edit?log=${updateLog.getId()}">Edit</a>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        No update logs yet
    </#list>
</div>
</@c.page>
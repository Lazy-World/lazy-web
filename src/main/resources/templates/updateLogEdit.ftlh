<#import "parts/common.ftlh" as c>
<#import "parts/updateLogForm.ftlh" as updLog>

<@c.page>
    <@updLog.updForm true/>

    <div class="col">
        <div class="card mx-auto mb-3" style="max-width: 540px;">
            <div class="row g-0">
                <div class="col-md-4">
                    <img src="/img/${currentLog.getFilename()}" class="img-fluid rounded-start" alt="script preview" />
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h5 class="card-title">${currentLog.getTitle()}</h5>
                        <p class="card-text">${currentLog.getText()}</p>
                    </div>
                </div>
                <div class="card-footer">
                    <small class="text-muted">Автор: <b>${currentLog.authorName}</b></small>
                </div>
            </div>
        </div>
    </div>
</@c.page>
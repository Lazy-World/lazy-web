<#import "parts/common.ftlh" as c>
<#import "parts/updateLogForm.ftlh" as updLog>

<@c.page>
    <@updLog.updForm true/>
    <div class="col mt-0" data-id="${currentLog.getId()}">
        <div class="card mx-auto my-4" style="width: 20rem;">
            <div class="card-body rounded-3">
                <h5 class="card-title">${currentLog.getTitle()}</h5>
                <p class="card-text">${currentLog.getText()}</p>
            </div>
        </div>
    </div>
</@c.page>
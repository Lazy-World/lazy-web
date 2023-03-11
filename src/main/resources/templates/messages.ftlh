<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="input-group mb-3" xmlns="http://www.w3.org/1999/html">
        <span class="input-group-text" id="basic-addon1">@</span>
        <div class="col-sm-2">
            <input type="text" class="form-control col-sm-3" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
        </div>
    </div>

    <#list users as user>
        <div class="my-3">
            <a class="nav-link" href="?sel=${user.getId()}">${user.username}</a>
        </div>
    </#list>

    <div class="row row-cols-md-1">
        <#list messages as message>
            <div class="col">
                <div class="card mx-auto mb-3" style="max-width: 540px;">
                    <div class="row g-0">
                        <div class="col-md-8">
                            <div class="card-body">
                                <p class="card-text">${message.getText()}</p>
                            </div>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">Posted by <b>${message.getAuthorName()}</b></small>
                            <button type="button" class="btn btn-outline-secondary">Edit</button>
                        </div>
                    </div>
                </div>
            </div>
        <#else>
            No messages yet
        </#list>
    </div>
</@c.page>
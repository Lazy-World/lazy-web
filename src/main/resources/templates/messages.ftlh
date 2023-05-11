<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>

<@c.page>
    <#if s.isUser>
        <div class="text-center">
            <a class="btn btn-light my-2" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                New Message
            </a>
        </div>
        <div class="collapse" id="collapseExample">
            <form method="post" enctype="multipart/form-data" class="col-md-3 mx-auto needs-validation">
                <div class="col mb-1">
                    <textarea type="text" name="text" class="form-control ${((errorMap['textError'])??)?string('is-invalid', '')}" value="<#if myMessage??>${myMessage.text}</#if>" placeholder="Message"></textarea>
                    <#if (errorMap['textError'])??>
                        <div class="invalid-feedback">
                            ${(errorMap['textError'])!''}
                        </div>
                    </#if>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked" checked="checked" />
                        <label class="form-check-label" for="flexSwitchCheckChecked">Create new chat</label>
                    </div>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <div class="mb-2">
                    <button class="btn btn-success" type="submit">Send</button>
                </div>
            </form>
        </div>
    </#if>

<#--    <div class="col cols-md-2">-->
<#--        <#list chats as chat>-->
<#--            <div class="col my-3">-->
<#--                <a class="nav-link" href="?sel=${chat.getSecondDialogMember(currentUser).getId()}">${chat.getName()}</a>-->
<#--            </div>-->
<#--        </#list>-->

    <div class="col cols-md-2" id="user-list">
        <#list users as user>
            <div class="col my-3" data-id="${user.getId()}">
                <a class="nav-link" href="?sel=${user.getId()}">${user.getUsername()}</a>
            </div>
        </#list>

        <div class="row row-cols-md-1" id="messages-history">
            <#if messages??>
                <#list messages as message>
                    <div class="col" data-id="${message.getId()}">
                        <div class="card mx-auto mb-3" style="max-width: 540px;">
                            <div class="row g-0">
                                <div class="col-md-8">
                                    <div class="card-body">
                                        <p class="card-text">${message.getText()}</p>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted"><b>${message.getMessageDateTimeString()}</b> Posted by <b>${message.getAuthorName()}</b></small>
                                    <button type="button" class="btn btn-outline-secondary">Edit</button>
                                </div>
                            </div>
                        </div>
                    </div>
                <#else>
                    No messages yet
                </#list>

            <#else>
                Chat not opened
            </#if>
        </div>
    </div>
</@c.page>
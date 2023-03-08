<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>

<@c.page>
    <#if s.isAdmin>
        <div class="text-center">
            <a class="btn btn-light my-2" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                New Post
            </a>
        </div>
        <div class="collapse" id="collapseExample">
            <form method="post" enctype="multipart/form-data" class="col-md-3 mx-auto needs-validation">
                <div class="col mb-1">
                    <input type="text" name="tag" class="form-control ${((errorMap['tagError'])??)?string('is-invalid', '')}" value="<#if updateLog??>${updateLog.tag}</#if>" placeholder="Title">
                    <#if (errorMap['tagError'])??>
                        <div class="invalid-feedback">
                            ${(errorMap['tagError'])!''}
                        </div>
                    </#if>
                </div>

                <div class="col mb-1">
                    <textarea type="text" name="text" class="form-control ${((errorMap['textError'])??)?string('is-invalid', '')}" id="validationCustom01" value="<#if updateLog??>${updateLog.text}</#if>" placeholder="Update log"></textarea>
                    <#if (errorMap['textError'])??>
                        <div class="invalid-feedback">
                            ${(errorMap['textError'])!''}
                        </div>
                    </#if>
                </div>

                <div class="col mb-2">
                    <input type="file" name="file" class="form-control" aria-label="file example">
                    <div class="invalid-feedback">Example invalid form file feedback</div>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <div class="mb-2">
                    <button class="btn btn-success" type="submit">Upload</button>
                </div>
            </form>
        </div>
    </#if>

    <div class="row row-cols-md-1">
        <#list updateLogs as updateLog>
            <div class="col">
                <div class="card mx-auto mb-3" style="max-width: 540px;">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="/img/${updateLog.filename}" class="img-fluid rounded-start" alt="script preview">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">${updateLog.tag}</h5>
                                <p class="card-text">${updateLog.text}</p>
                            </div>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">Posted by <b>${updateLog.authorName}</b></small>
                        </div>
                    </div>
                </div>
            </div>
        <#else>
            No update logs yet
        </#list>
    </div>
</@c.page>
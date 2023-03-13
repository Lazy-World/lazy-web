<#macro updForm isLogEdit>
    <div class="text-center">
        <a class="btn btn-light my-2" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
            <#if isLogEdit>
                Edit update log
            <#else>
                New update log
            </#if>
        </a>
    </div>

    <div class="collapse" id="collapseExample">
        <form method="post" enctype="multipart/form-data" class="col-md-3 mx-auto needs-validation">
            <div class="col mb-1">
                <input type="text" name="tag" class="form-control ${((errorMap['tagError'])??)?string('is-invalid', '')}" value="<#if currentLog??>${currentLog.tag}</#if>" placeholder="Title">
                <#if (errorMap['tagError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['tagError'])!''}
                    </div>
                </#if>
            </div>

            <div class="col mb-1">
                <textarea type="text" name="text" class="form-control ${((errorMap['textError'])??)?string('is-invalid', '')}" placeholder="Update log"><#if currentLog??>${currentLog.text}</#if></textarea>
                <#if (errorMap['textError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['textError'])!''}
                    </div>
                </#if>
            </div>

            <div class="col mb-2">
                <input type="file" name="file" class="form-control ${((errorMap['fileError'])??)?string('is-invalid', '')}" aria-label="file example">
                <div class="invalid-feedback">Example invalid form file feedback</div>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="mb-2">
                <button class="btn btn-success" type="submit">
                    <#if isLogEdit>
                        Save
                    <#else>
                        Upload
                    </#if>
                </button>
            </div>
        </form>
    </div>
</#macro>
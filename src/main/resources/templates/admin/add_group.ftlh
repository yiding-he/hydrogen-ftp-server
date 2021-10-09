<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, ftpUser-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/global.css">
    <title>Hydrogen FTP Server Management</title>
</head>
<body>

<#assign title = 'Add Group'>
<#assign action = 'add_group'>
<#if group??>
    <#assign title = 'Edit Group'>
    <#assign action = 'edit_group'>
</#if>

<form action="${action}" method="post">
    <#if group??>
        <input type="hidden" name="groupId" value="${group.id?long?c}">
    </#if>
    <div class="titled-pane">
        <div class="title">${title}</div>
        <div class="body">
            <div class="form-field">
                <span class="field-label">Group Name:</span>
                <input type="text" name="groupName" id="groupName"
                       value="${(group.groupName)!}" <#if group??>readonly</#if>>
            </div>
            <div class="form-field" style="margin-top: 0.5em">
                <span class="field-label">Users:</span><br/>
                <div class="checkbox-group">
                    <#list users as user>
                        <div class="checkbox-line">
                            <label for="user_${user.id?long?c}">
                                <input type="checkbox" name="userId"
                                       <#if user.selected>checked</#if>
                                       value="${user.id?long?c}" id="user_${user.id?long?c}">
                                ${user.displayName}
                            </label>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
        <div class="footer">
            <button type="submit">Save</button>
            <button onclick="window.history.back();return false;">Back</button>
        </div>
    </div>
</form>
</body>
</html>
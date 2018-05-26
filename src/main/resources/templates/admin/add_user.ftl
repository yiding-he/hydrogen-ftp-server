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

<#assign title = 'Add User'>
<#assign action = 'add_user'>
<#if ftpUser??>
    <#assign title = 'Edit User'>
    <#assign action = 'edit_user'>
</#if>

<form action="${action}" method="post">
    <#if ftpUser??>
        <input type="hidden" name="id" value="${ftpUser.id?long?c}">
    </#if>
    <div class="titled-pane">
        <div class="title">${title}</div>
        <div class="body">
            <div class="form-field">
                <span class="field-label">User Name:</span>
                <input type="text" name="username" id="username" value="${(ftpUser.username)!}" <#if ftpUser??>readonly</#if>>
            </div>
            <div class="form-field">
                <span class="field-label">Display Name:</span>
                <input type="text" name="displayName" id="displayName" value="${(ftpUser.displayName)!}">
            </div>
            <div class="form-field">
                <span class="field-label">Password:</span>
                <input type="password" name="password" id="password">
            </div>
        </div>
        <div class="footer">
            <button type="submit">Save</button>
        </div>
    </div>
</form>
</body>
</html>
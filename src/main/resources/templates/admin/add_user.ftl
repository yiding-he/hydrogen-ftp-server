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
<#if user??>
    <#assign title = 'Edit User'>
    <#assign action = 'edit_user'>
</#if>

<form action="${action}" method="post">
    <#if user??>
        <input type="hidden" name="id" value="${user.id?long?c}">
    </#if>
    <div class="titled-pane">
        <div class="title">${title}</div>
        <div class="body">
            <div class="form-field">
                <span class="field-label">User Name:</span>
                <input type="text" name="username" id="username"
                       value="${(user.username)!}" <#if user??>readonly</#if>>
            </div>
            <div class="form-field">
                <span class="field-label">Display Name:</span>
                <input type="text" name="displayName" id="displayName" value="${(user.displayName)!}">
            </div>
            <div class="form-field">
                <span class="field-label">Password:</span>
                <input type="password" name="password" id="password">
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
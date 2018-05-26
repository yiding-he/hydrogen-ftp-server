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
<div class="titled-pane">
    <div class="title">Users</div>
    <div class="body">
        <table class="datatable">
            <thead>
            <tr>
                <td>User Name</td>
                <td>Display Name</td>
                <td>Groups</td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <#if users??>
                <#list users as ftpUser>
                <tr>
                    <td>${ftpUser.username}</td>
                    <td>${ftpUser.displayName}</td>
                    <td>${ftpUser.groups!!}</td>
                    <td>
                        <a href="edit_user?userId=${ftpUser.id?long?c}"><button>Edit</button></a>
                        <button>Delete</button>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
    <div class="footer">
        <a href="add_user">
            <button>Add User</button>
        </a>
    </div>
</div>
<div class="titled-pane">
    <div class="title">User Groups</div>
    <div class="body">
    </div>
    <div class="footer">
        <a href="add_group">
            <button>Add User Group</button>
        </a>
    </div>
</div>
<div class="titled-pane">
    <div class="title">Server</div>
    <div class="body">
        <#if server_running == true>
            Server is running.
        <#else>
            Server is stopped.
        </#if>

    </div>
    <div class="footer">
        <form action="server_action" method="post" onsubmit="return confirm('Are you sure?')">
            <#if server_running == true>
                <button type="submit" name="action" value="stop">Stop Server</button>
            <#else>
                <button type="submit" name="action" value="start">Start Server</button>
            </#if>
        </form>
    </div>
</div>
</body>
</html>
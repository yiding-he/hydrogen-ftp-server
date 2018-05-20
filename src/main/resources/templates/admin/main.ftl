<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/global.css">
    <title>Hydrogen FTP Server Management</title>
</head>
<body>
<div class="titled-pane">
    <div class="title">Users</div>
    <div class="body">
        <table>
            <thead>
            <td>User Name</td>
            <td>Display Name</td>
            <td>Groups</td>
            <td> </td>
            </thead>
            <tbody>
            <#if users??>
                <#list users as user>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
    <div class="footer">
        <a href="add_user"><button>Add User</button></a>
    </div>
</div>
<div class="titled-pane">
    <div class="title">User Groups</div>
    <div class="body">
    </div>
    <div class="footer">
        <a href="add_group"><button>Add User Group</button></a>
    </div>
</div>
</body>
</html>
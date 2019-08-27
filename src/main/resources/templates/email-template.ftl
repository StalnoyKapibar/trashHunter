<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>TrashHunter</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <style>
        body {
            font-family: 'Open Sans', Arial, sans-serif;
            font-size: 24px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">

    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="border-collapse: collapse;">
        <tr>

            <p><b>Привет ${name},</b></p>
                <p>${body}</b></p>
                <#if link??>
                    <a href="${link}">Ссылка</a>
                 </#if>
            </td>
        </tr>
    </table>

</body>
</html>
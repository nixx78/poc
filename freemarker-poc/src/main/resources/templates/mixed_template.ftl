<#ftl output_format="JSON">
<#assign main_table>
<table>
    <thead>
    <th>ID</th>
    <th>Name</th>
    <th>Date of Birth</th>
    </thead>

    <tbody>
    <#foreach c in customers>
    <tr>
        <td>${c.id}</td>
        <td>${c.name} ${c.surname}</td>
        <td>${(c.dateOfBirth?string('dd/MM/yyy'))!}</td>
    </tr>
    </#foreach>
    </tbody>
</table>
</#assign>
{
"editable":"${type?join(", ", "None")}",
"staticData":"<#outputformat "HTML">${main_table}</#outputformat>"
}
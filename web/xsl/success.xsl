<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title> success </title>
            </head>

            <body>
                <h2> <xsl:value-of select="view/header/title"/> </h2>
                <form name="{view/body/form/name}" action="{view/body/form/action}" method="{view/body/form/method}" >
                    <xsl:for-each select="view/body/form/textView">
                        <label> <xsl:value-of select="label"/> </label>
                        <input name="{name}" type="text" value="{value}"/>
                        <br/>
                    </xsl:for-each>
                    <label> <xsl:value-of select="view/body/form/buttonView/label"/> </label>
                    <input type="submit" name="{view/body/form/buttonView/name}"/>
                </form>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
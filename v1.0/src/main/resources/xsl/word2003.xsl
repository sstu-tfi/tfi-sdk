<?xml version="1.0" encoding="UTF-8"?>

<!--
	Document   : word2idf.xsl
	Created on : 16/02/2008
	Author     : Denis Murashev
	Version    : 1.0
	Description: Converts MS Word into TFI internal document format (IDF).
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
		xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
		xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
		xmlns:v="urn:schemas-microsoft-com:vml"
		xmlns:st1="urn:schemas-microsoft-com:office:smarttags"
		xmlns:o="urn:schemas-microsoft-com:office:office">
	<xsl:output method="xml" encoding="UTF-8"/>
	<xsl:template match="w:wordDocument">
		<xsl:element name="doc">
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="w:p">
		<xsl:element name="p">
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="st1:metricconverter">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="w:r">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="w:t">
		<xsl:call-template name="font"/>
	</xsl:template>
	<xsl:template match="w:tbl">
		<xsl:element name="table">
			<xsl:apply-templates select="w:tr"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="w:tr">
		<xsl:element name="tr">
			<xsl:apply-templates select="w:tc"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="w:tc">
		<xsl:element name="td">
			<xsl:if test="w:tcPr/w:gridSpan">
				<xsl:attribute name="colspan">
					<xsl:value-of select="w:tcPr/w:gridSpan/@w:val"/>
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="w:tc/w:p">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template name="bold">
		<xsl:choose>
			<xsl:when test="../w:rPr/w:b">
				<xsl:element name="b">
					<xsl:value-of select="."/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="."/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="italic">
		<xsl:choose>
			<xsl:when test="../w:rPr/w:i">
				<xsl:element name="i">
					<xsl:call-template name="bold"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="bold"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="underlined">
		<xsl:choose>
			<xsl:when test="../w:rPr/w:u">
				<xsl:element name="u">
					<xsl:call-template name="italic"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="italic"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="superscript">
		<xsl:choose>
			<xsl:when test="../w:rPr/w:vertAlign/@w:val = 'superscript'">
				<xsl:element name="sup">
					<xsl:call-template name="underlined"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="underlined"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="subscript">
		<xsl:choose>
			<xsl:when test="../w:rPr/w:vertAlign/@w:val = 'subscript'">
				<xsl:element name="sub">
					<xsl:call-template name="superscript"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="superscript"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="font">
		<xsl:choose>
			<xsl:when test="../w:rPr/wx:font">
				<xsl:element name="span">
					<xsl:attribute name="font">
						<xsl:value-of select="../w:rPr/wx:font/@wx:val"/>
					</xsl:attribute>
					<xsl:call-template name="subscript"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="subscript"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="w:pict">
		<xsl:choose>
			<xsl:when test="./w:binData">
				<xsl:element name="img">
					<xsl:attribute name="src">
						<xsl:value-of select="./v:shape/v:imagedata/@src"/>
					</xsl:attribute>
					<xsl:value-of select="./w:binData"/>
				</xsl:element>
			</xsl:when>
			<xsl:otherwise>
				<xsl:element name="imgRef">
					<xsl:attribute name="src">
						<xsl:value-of select="./v:shape/v:imagedata/@src"/>
					</xsl:attribute>
				</xsl:element>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>

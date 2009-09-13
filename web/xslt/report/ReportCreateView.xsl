<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE uridef[
	<!ENTITY owl "http://www.w3.org/2002/07/owl#">
	<!ENTITY rdf "http://www.w3.org/1999/02/22-rdf-syntax-ns#">
	<!ENTITY rdfs "http://www.w3.org/2000/01/rdf-schema#">
	<!ENTITY xsd "http://www.w3.org/2001/XMLSchema#">
	<!ENTITY geo "http://www.w3.org/2003/01/geo/wgs84_pos#">
	<!ENTITY sparql "http://www.w3.org/2005/sparql-results#">
]>
<xsl:stylesheet version="1.0"
xmlns="http://www.w3.org/1999/xhtml"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:owl="&owl;"
xmlns:rdf="&rdf;"
xmlns:rdfs="&rdfs;"
xmlns:xsd="&xsd;"
xmlns:sparql="&sparql;"
exclude-result-prefixes="owl rdf rdfs xsd sparql">

	<xsl:import href="../sparql2google-wire.xsl"/>

	<xsl:include href="../FrontEndView.xsl"/>

	<xsl:param name="query-result"/>
	<xsl:param name="visualization-result"/>
	<xsl:param name="query-string" select="''"/>

	<xsl:template name="title">
		Create report
	</xsl:template>

	<xsl:template name="content">
		<div id="main">
			<h2><xsl:call-template name="title"/></h2>

			<xsl:copy-of select="document('arg://report')"/>

			<form action="{$resource//sparql:binding[@name = 'resource']/sparql:uri}" method="get" accept-charset="UTF-8">
				<p>
					<input type="hidden" name="view" value="create"/>
					<label for="query-string">Query</label>
					<br/>
					<textarea cols="80" rows="20" id="query-string" name="query-string">
						<xsl:if test="$query-result">
							<xsl:value-of select="$query-string"/>
						</xsl:if>
					</textarea>
					<br/>
					<label for="title">Title</label>
					<input type="text" id="title" name="title" value="whatever!!"/>
					<br/>
					<label for="endpoint">Endpoint</label>
					<input type="text" id="endpoint" name="endpoint" value="http://dbpedia.org/sparql"/>
					<br/>
					<button type="submit" name="action" value="query">Query</button>
					<button type="submit" name="action" value="save">Save</button>
				</p>
				
				<fieldset>
					<legend>Visualizations</legend>
					<ul>
						<li>
							<input type="checkbox" id="table-option" name="visualization" value="table" checked="checked"/>
							<label for="table-option">Table</label>
						</li>
						<li>
							<input type="checkbox" id="scatter-chart-option" name="visualization" value="scatter-chart" onclick="toggleScatterChart(this.checked);"/>
							<label for="scatter-chart-option">Scatter chart</label>
						</li>
						<li>
							<input type="checkbox" id="line-chart-option" name="visualization" value="line-chart"/>
							<label for="line-chart-option">Line chart</label>
						</li>
						<li>
							<input type="checkbox" id="pie-chart-option" name="visualization" value="pie-chart"/>
							<label for="pie-chart-option">Pie chart</label>
						</li>
						<li>
							<input type="checkbox" id="map-option" name="visualization" value="map"/>
							<label for="map-option">Map</label>
						</li>
					</ul>
				</fieldset>

		<div id="table"></div>

				<fieldset id="scatter-chart-controls">
					<legend>Scatter chart</legend>
					<p>
						<label for="scatter-chart-x-binding">X binding</label>
						<select id="scatter-chart-x-binding" name="scatter-chart-x-binding" onchange="drawScatterChart(getSelectedValues(this)[0], getSelectedValues(document.getElementById('scatter-chart-y-binding')));">
							<!-- filled out in JavaScript -->
						</select>
						<label for="scatter-chart-y-binding">Y bindings</label>
						<select id="scatter-chart-y-binding" name="scatter-chart-y-binding" multiple="multiple" onchange="drawScatterChart(getSelectedValues(document.getElementById('scatter-chart-x-binding'))[0], getSelectedValues(this));">
							<!-- filled out in JavaScript -->
						</select>
						<input type="hidden" name="visualization" value="scatter-chart"/>
						<button type="submit" name="action" value="update">Update</button>
					</p>
				</fieldset>

		<div id="scatter-chart" style="width: 800px; height: 400px;"></div>

				<fieldset>
					<legend>Line chart</legend>
					<p>
						<label for="line-chart-label-binding">Label binding</label>
						<select id="line-chart-label-binding" name="line-chart-label-binding">
							<!-- filled out in JavaScript -->
						</select>
						<label for="line-chart-value-binding">Value bindings</label>
						<select id="line-chart-value-binding" name="line-chart-value-binding" multiple="multiple">
							<!-- filled out in JavaScript -->
						</select>
						<input type="hidden" name="visualization" value="line-chart"/>
						<button type="submit" name="action" value="update">Update</button>
					</p>
				</fieldset>

		<div id="line-chart" style="width: 800px; height: 400px;"></div>

				<fieldset>
					<legend>Pie chart</legend>
					<p>
						<label for="pie-chart-label-binding">Label binding</label>
						<select id="pie-chart-label-binding" name="pie-chart-label-binding">
							<!-- filled out in JavaScript -->
						</select>
						<label for="pie-chart-value-binding">Value binding</label>
						<select id="pie-chart-value-binding" name="pie-chart-value-binding">
							<!-- filled out in JavaScript -->
						</select>
						<input type="hidden" name="visualization" value="pie-chart"/>
						<button type="submit" name="action" value="update">Update</button>
					</p>
				</fieldset>

		<div id="pie-chart" style="width: 800px; height: 400px;"></div>

				<fieldset>
					<legend>Map</legend>
					<p>
						<label for="map-lat-binding">Latitude binding</label>
						<select id="map-lat-binding" name="map-lat-binding">
							<!-- filled out in JavaScript -->
						</select>
						<label for="map-lng-binding">Longitude binding</label>
						<select id="map-lng-binding" name="map-lng-binding">
							<!-- filled out in JavaScript -->
						</select>
						<input type="hidden" name="visualization" value="map"/>
						<button type="submit" name="action" value="update">Update</button>
					</p>
				</fieldset>

		<div id="map" style="width: 800px; height: 400px;"></div>

			</form>
		</div>
	</xsl:template>

</xsl:stylesheet>
<!DOCTYPE html>
<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap-cosmo-theme.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap-select.min.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/custom.css'/>" />

    <title>Food Hygiene Ratings</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/messageModal.jspf"%>
<nav id="navBar" class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="glyphicon glyphicon-menu-hamburger"></span>
            </button>
            <span class="navbar-brand pull-right"> Food Hygiene Ratings in the UK</span>
        </div>
    </div>
</nav>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="row">
                <div class="col-sm-4 col-xs-12">
                    <div class="panel-title"><h4 id="panelTitle">Food Hygiene Ratings</h4></div>
                </div>
            </div>
        </div>
        <div class="panel-body">
            <div id="authoritiesContainer">
            </div>

            <div id="percentageContainer">
            </div>
        </div>
    </div>
</div>

<script id="authoritiesSelectTemplate" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-sm-4 col-xs-6 form-group">
            <label for="authoritiesSelect" class="mini-label">Local Authorities</label>
            <select id="authoritiesSelect" class="form-control filter-field selectpicker" data-style="btn-secondary"">
                <option value="-1">Select Authority</option>
                {{#each authorities}}
                <option region="{{RegionName}}" value="{{LocalAuthorityId}}">{{Name}}</option>
                {{/each}}
            </select>
        </div>
    </div>
</script>

<script id="percentageTemplate" type="text/x-handlebars-template">
    <div class='border-bottom text-primary'><span class='title-large'>(%) Hygiene ratings in {{setRegion}}</span></div>
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <thead>
            <th>Ratings</th>
            <th>Percentage</th>
            </thead>
            <tbody>
            {{#each this}}
            <tr><td>{{setTitle rating}}</td>
                <td>{{percentage}}</td></tr>
            {{/each}}
            </tbody>
        </table>
    </div>
</script>

<script id="validationPopoverTemplate" type="text/x-handlebars-template">
    <div class="popover validation-popover" role="tooltip">
        <div class="arrow"></div>
        <div class="popover-content"></div>
    </div>
</script>

<script id="loaderTemplate" type="text/x-handlebars-template">
    <div id="loader" class="row">
        <div class="col-xs-12">
            <div class="loading">Please Wait...</div>
        </div>
    </div>
</script>

<script>
    var	contextPath = '${pageContext.request.contextPath}';
</script>

<script src="<c:url value='/js/jquery.2.2.4.min.js'/>"></script>
<script src="<c:url value='/js/bootstrap.3.3.6.min.js'/>"></script>
<script src="<c:url value='/js/bootstrap-select.min.js'/>"></script>
<script src="<c:url value='/js/handlebars-v4.0.5.js'/>"></script>
<script src="<c:url value='/js/pages/hygieneRating.js'/>"></script>
<script src="<c:url value='/js/common.js'/>"></script>
</body>
</html>
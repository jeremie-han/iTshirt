<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(".sortable").sortable({
	connectWith: 'div'
	});
</script>
</head>
<body>
<div class="card-columns">

  <div class="sortable">
    <!--Panel-->
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Panel title that wraps to a new line</h5>
        <p class="card-text">This is a longer panel with supporting text below as a natural
          lead-in
          to
          additional content. This content is a little bit longer.</p>
      </div>
    </div>
    <!--/.Panel-->
  </div>

  <div class="sortable">
    <!--Panel-->
    <div class="card p-3">
      <blockquote class="blockquote mb-0 card-body">
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a
          ante.</p>
        <footer class="blockquote-footer">
          <small class="text-muted">
            Someone famous in <cite title="Source Title">Source Title</cite>
          </small>
        </footer>
      </blockquote>
    </div>
    <!--/.Panel-->
  </div>

  <div class="sortable">
    <!--Panel-->
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Panel title</h5>
        <p class="card-text">This panel has supporting text below as a natural lead-in to
          additional
          content.</p>
        <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
      </div>
    </div>
    <!--/.Panel-->
  </div>

  <div class="sortable">
    <!--Panel-->
    <div class="card bg-primary text-white text-center p-3">
      <blockquote class="blockquote mb-0">
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat.</p>
        <footer class="blockquote-footer">
          <small>
            Someone famous in <cite title="Source Title">Source Title</cite>
          </small>
        </footer>
      </blockquote>
    </div>
    <!--/.Panel-->
  </div>

  <div class="sortable">
    <!--Panel-->
    <div class="card text-center">
      <div class="card-body">
        <h5 class="card-title">Panel title</h5>
        <p class="card-text">This panel has supporting text below as a natural lead-in to
          additional
          content.</p>
        <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
      </div>
    </div>
    <!--/.Panel-->
  </div>

  <div class="sortable">
    <!--Panel-->
    <div class="card p-3 text-right">
      <blockquote class="blockquote mb-0">
        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a
          ante.</p>
        <footer class="blockquote-footer">
          <small class="text-muted">
            Someone famous in <cite title="Source Title">Source Title</cite>
          </small>
        </footer>
      </blockquote>
    </div>
    <!--/.Panel-->
  </div>

  <div class="sortable">
    <!--Panel-->
    <div class="card">
      <div class="card-body">
        <h5 class="card-title">Panel title</h5>
        <p class="card-text">This is a wider panel with supporting text below as a natural
          lead-in
          to
          additional content. This panel has even longer content than the first to show that
          equal
          height action.</p>
        <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
      </div>
    </div>
    <!--/.Panel-->
  </div>
</div>
</body>
</html>
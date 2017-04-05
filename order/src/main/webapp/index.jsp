<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
    <head>
        <title>song</title>
        <link href="css/bootstrap/3.3.7/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="wrap">
        <div class="container">
            <div class="row">
                <div class="span2">
                    <h2>
                        标题
                    </h2>
                    <p>
                        本可视化布局程序在HTML5浏览器上运行更加完美, 能实现自动本地化保存, 即使关闭了网页, 下一次打开仍然能恢复上一次的操作.
                    </p>
                    <p>
                        <a class="btn" href="#">查看更多 »</a>
                    </p>
                </div>
                <div class="span6">
                    <ul class="breadcrumb">
                        <li>
                            <a href="#">主页</a> <span class="divider">/</span>
                        </li>
                        <li>
                            <a href="#">类目</a> <span class="divider">/</span>
                        </li>
                        <li class="active">
                            主题
                        </li>
                    </ul>
                    <div class="hero-unit">
                        <h1>
                            Hello, world!
                        </h1>
                        <p>
                            这是一个可视化布局模板, 你可以点击模板里的文字进行修改, 也可以通过点击弹出的编辑框进行富文本修改. 拖动区块能实现排序.
                        </p>
                        <p>
                            <a class="btn btn-primary btn-large" href="#">参看更多 »</a>
                        </p>
                    </div>
                </div>
                <div class="span4">
                    <div class="carousel slide" id="carousel-130412">
                        <ol class="carousel-indicators">
                            <li class="active" data-slide-to="0" data-target="#carousel-130412">
                            </li>
                            <li data-slide-to="1" data-target="#carousel-130412">
                            </li>
                            <li data-slide-to="2" data-target="#carousel-130412">
                            </li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="item active">
                                <img alt="" src="img/1.jpg" />
                                <div class="carousel-caption">
                                    <h4>
                                        棒球
                                    </h4>
                                    <p>
                                        棒球运动是一种以棒打球为主要特点，集体性、对抗性很强的球类运动项目，在美国、日本尤为盛行。
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <img alt="" src="img/2.jpg" />
                                <div class="carousel-caption">
                                    <h4>
                                        冲浪
                                    </h4>
                                    <p>
                                        冲浪是以海浪为动力，利用自身的高超技巧和平衡能力，搏击海浪的一项运动。运动员站立在冲浪板上，或利用腹板、跪板、充气的橡皮垫、划艇、皮艇等驾驭海浪的一项水上运动。
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <img alt="" src="img/3.jpg" />
                                <div class="carousel-caption">
                                    <h4>
                                        自行车
                                    </h4>
                                    <p>
                                        以自行车为工具比赛骑行速度的体育运动。1896年第一届奥林匹克运动会上被列为正式比赛项目。环法赛为最著名的世界自行车锦标赛。
                                    </p>
                                </div>
                            </div>
                        </div> <a data-slide="prev" href="#carousel-130412" class="left carousel-control">‹</a> <a data-slide="next" href="#carousel-130412" class="right carousel-control">›</a>
                    </div>
                </div>
            </div>
        </div>


        <script src="/js/jquery-3.2.0.min.js"/>
        <script src="/js/bootstrap/3.3.7/bootstrap.min.js"/>
    </body>
</html>
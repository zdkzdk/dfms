<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Purple Admin</title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="../../node_modules/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="../../node_modules/perfect-scrollbar/dist/css/perfect-scrollbar.min.css">
    <!-- endinject -->
    <!-- plugin css for this page -->
    <link rel="stylesheet" href="../../node_modules/jqvmap/dist/jqvmap.min.css"/>
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="../../css/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="images/favicon.png"/>

    <link rel="stylesheet" href="<%=path %>/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script src="<%=path %>/node_modules/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/jquery.ztree.all.js"></script>
    <script>

        function userUpdShow(userId) {
            $.ajax({
                url : "/user/upd/show",
                data: "userId=" + userId,
                success: function (data) {
                    $("#myModalUpd :input[name='userId']").val(data.user.userId);
                    $("#myModalUpd :input[name='username']").val(data.user.username);
                    $("#myModalUpd :input[name='realname']").val(data.user.realname);
                    $("#myModalUpd :input[name='phone']").val(data.user.phone);
                    //zTree
                    //z-tree
                    var zTreeObj;
                    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
                    var setting = {
                        check: {
                            enable: true,
                            chkStyle: "checkbox",
                            chkboxType: {"Y": "ps", "N": "ps"}
                        },
                        callback: {    //第一步
                            onClick: zTreeOnClick,
                            beforeCheck: true,
                            onCheck: zTreeOnClick

                        },

                        data: {
                            keep: {
                                leaf: false,
                                parent: true
                            },
                            simpleData: {
                                enable: true,
                                idKey: "id",
                                pIdKey: "pId",
                                rootPId: null
                            }
                        }
                    };

                    function zTreeOnClick(event, treeId, treeNode) {       //第二步
                        setFormInput();
                    }


                    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
                    //？？？如果放在ajax的回调函数中，就不要再加就绪函数了
                    var zNodes =  data.roles;
                    zTreeObj = $.fn.zTree.init($("#treeDemoUpd"), setting, zNodes);



                    // //给z-tree赋值
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemoUpd");//根据 treeId 获取 zTree 对象
                    //var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
                    var roleSelf = data.roleSelf;
                    for (var role in roleSelf) {
                        var nodes = treeObj.getNodeByParam("id", roleSelf[role].roleId, null);//根据节点数据的属性(id)获取条件完全匹配的节点数据 JSON 对象集合

                        treeObj.checkNode(nodes, true, false, false);
                    }
                }
            })
        }
        function smPrepare() {
            setFormInput();
            return true;
        }
        function setFormInput() {
            var treeObjUpd = $.fn.zTree.getZTreeObj("treeDemoUpd");
            var nodesUpd = treeObjUpd.getCheckedNodes(true);

            var idsUpd = getIds(nodesUpd);

            $("#roleIdsUpd").val(idsUpd);
        }

        function getIds(nodes) {
            v = "";
            var ids = '';
            for (var i = 0; i < nodes.length; i++) {
                v += nodes[i].name + ",";
                // console.log("节点id:" + nodes[i].id + "节点名称" + v); //获取选中节点的值
                if (i == (nodes.length - 1)) {
                    ids += nodes[i].id
                } else {
                    ids += nodes[i].id + ','
                }
            }
            return ids;
        }
        //删除role
        function delRole(roleId) {
            window.location.href = "/user/del?userId=" + roleId;
        }

    </script>
</head>

<body>
<div class="container-scroller">
    <!-- partial:../../partials/_navbar.html -->
    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            <a class="navbar-brand brand-logo" href="../../index.html"><img src="../../images/logo.svg" alt="logo"/></a>
            <a class="navbar-brand brand-logo-mini" href="../../index.html"><img src="../../images/logo-mini.svg"
                                                                                 alt="logo"/></a>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-stretch">
            <div class="search-field ml-4 d-none d-md-block">
                <form class="d-flex align-items-stretch h-100" action="#">
                    <div class="input-group">
                        <input type="text" class="form-control bg-transparent border-0" placeholder="Search">
                        <div class="input-group-btn">
                            <button type="button" class="btn bg-transparent dropdown-toggle px-0" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false">
                                <i class="mdi mdi-earth"></i>
                            </button>
                            <div class="dropdown-menu dropdown-menu-right">
                                <a class="dropdown-item" href="#">Today</a>
                                <a class="dropdown-item" href="#">This week</a>
                                <a class="dropdown-item" href="#">This month</a>
                                <div role="separator" class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Month and older</a>
                            </div>
                        </div>
                        <div class="input-group-addon bg-transparent border-0 search-button">
                            <button type="submit" class="btn btn-sm bg-transparent px-0">
                                <i class="mdi mdi-magnify"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item d-none d-lg-block full-screen-link">
                    <a class="nav-link">
                        <i class="mdi mdi-fullscreen" id="fullscreen-button"></i>
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link count-indicator dropdown-toggle" id="messageDropdown" href="#"
                       data-toggle="dropdown" aria-expanded="false">
                        <i class="mdi mdi-email-outline"></i>
                        <span class="count"></span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
                         aria-labelledby="messageDropdown">
                        <h6 class="p-3 mb-0">Messages</h6>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <img src="../../images/faces/face4.jpg" alt="image" class="profile-pic">
                            </div>
                            <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                                <h6 class="preview-subject ellipsis mb-1 font-weight-normal">Mark send you a
                                    message</h6>
                                <p class="text-gray mb-0">
                                    1 Minutes ago
                                </p>
                            </div>
                        </a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <img src="../../images/faces/face2.jpg" alt="image" class="profile-pic">
                            </div>
                            <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                                <h6 class="preview-subject ellipsis mb-1 font-weight-normal">Cregh send you a
                                    message</h6>
                                <p class="text-gray mb-0">
                                    15 Minutes ago
                                </p>
                            </div>
                        </a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <img src="../../images/faces/face3.jpg" alt="image" class="profile-pic">
                            </div>
                            <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                                <h6 class="preview-subject ellipsis mb-1 font-weight-normal">Profile picture
                                    updated</h6>
                                <p class="text-gray mb-0">
                                    18 Minutes ago
                                </p>
                            </div>
                        </a>
                        <div class="dropdown-divider"></div>
                        <h6 class="p-3 mb-0 text-center">4 new messages</h6>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link count-indicator dropdown-toggle" id="notificationDropdown" href="#"
                       data-toggle="dropdown">
                        <i class="mdi mdi-bell-outline"></i>
                        <span class="count"></span>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list"
                         aria-labelledby="notificationDropdown">
                        <h6 class="p-3 mb-0">Notifications</h6>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-success">
                                    <i class="mdi mdi-calendar"></i>
                                </div>
                            </div>
                            <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                                <h6 class="preview-subject font-weight-normal mb-1">Event today</h6>
                                <p class="text-gray ellipsis mb-0">
                                    Just a reminder that you have an event today
                                </p>
                            </div>
                        </a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-warning">
                                    <i class="mdi mdi-settings"></i>
                                </div>
                            </div>
                            <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                                <h6 class="preview-subject font-weight-normal mb-1">Settings</h6>
                                <p class="text-gray ellipsis mb-0">
                                    Update dashboard
                                </p>
                            </div>
                        </a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item preview-item">
                            <div class="preview-thumbnail">
                                <div class="preview-icon bg-info">
                                    <i class="mdi mdi-link-variant"></i>
                                </div>
                            </div>
                            <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                                <h6 class="preview-subject font-weight-normal mb-1">Launch Admin</h6>
                                <p class="text-gray ellipsis mb-0">
                                    New admin wow!
                                </p>
                            </div>
                        </a>
                        <div class="dropdown-divider"></div>
                        <h6 class="p-3 mb-0 text-center">See all notifications</h6>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle nav-profile" id="profileDropdown" href="#" data-toggle="dropdown"
                       aria-expanded="false">
                        <img src="../../images/faces/face1.jpg" alt="image">
                        <span class="d-none d-lg-inline">Daniel Russiel</span>
                    </a>
                    <div class="dropdown-menu navbar-dropdown w-100" aria-labelledby="profileDropdown">
                        <a class="dropdown-item" href="#">
                            <i class="mdi mdi-cached mr-2 text-success"></i>
                            Activity Log
                        </a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">
                            <i class="mdi mdi-logout mr-2 text-primary"></i>
                            Signout
                        </a>
                    </div>
                </li>
                <li class="nav-item nav-logout d-none d-lg-block">
                    <a class="nav-link" href="#">
                        <i class="mdi mdi-power"></i>
                    </a>
                </li>
            </ul>
            <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
                    data-toggle="offcanvas">
                <span class="mdi mdi-menu"></span>
            </button>
        </div>
    </nav>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <div class="row row-offcanvas row-offcanvas-right">
            <!-- partial:../../partials/_sidebar.html -->

            <nav class="sidebar sidebar-offcanvas" id="sidebar">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/user/list">
                            <span class="menu-title">用户管理</span>
                            <i class="mdi mdi-home menu-icon"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"  href="/role/list">
                            <span class="menu-title">角色管理</span>
                            <i class="menu-arrow"></i>
                            <i class="mdi mdi-crosshairs-gps menu-icon"></i>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/permission/list">
                            <span class="menu-title">权限管理</span>
                            <i class="mdi mdi-contacts menu-icon"></i>
                        </a>
                    </li>
                </ul>
                <div class="sidebar-progress">
                    <p>Total Sales</p>
                    <div class="progress progress-sm">
                        <div class="progress-bar bg-gradient-success" role="progressbar" style="width: 72%"
                             aria-valuenow="72" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <p>50 Items sold</p>
                </div>
                <div class="sidebar-progress">
                    <p>Customer Target</p>
                    <div class="progress progress-sm">
                        <div class="progress-bar bg-gradient-primary" role="progressbar" style="width: 90%"
                             aria-valuenow="90" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <p>200 Items sold</p>
                </div>
            </nav>

            <!-- partial -->
            <div class="content-wrapper">
                <div class="row">
                    <div class="col-lg-12 grid-margin stretch-card">
                        <div class="card">
                            <div class="card-body">
                                <h4 class="card-title">用户列表</h4>
                                <p class="card-description">

                                </p>
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th style="width: 8%;">编号</th>
                                        <th style="width: 10%;">账号</th>
                                        <th style="width: 14%;">真实姓名</th>
                                        <th style="width: 13%;">phone</th>
                                        <th style="width: 18%;">email</th>
                                        <th style="width: 8%;">性别</th>
                                        <th style="width: 18%;">状态(是否锁定)</th>
                                        <th>修改</th>
                                        <th>删除</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="user" varStatus="status">
                                    <c:if test="${status.count%2 == 0}"><tr class="table-info"></c:if>
                                    <c:if test="${status.count%2 != 0}"><tr class="table-success"></c:if>
                                        <td>${status.count}</td>
                                        <td>${user.username}</td>
                                        <td>${user.realname}</td>
                                        <td>${user.phone}</td>
                                        <td>${user.email}</td>
                                        <td><c:if test="${user.sex == 1}">男</c:if> <c:if test="${user.sex == 0}">女</c:if></td>
                                        <td><c:if test="${user.locked == 1}">锁定</c:if> <c:if test="${user.locked == 0}">激活</c:if></td>
  <%--                                      <td>${role.ctime}</td>--%>
                                        <td><button type="button" class="btn btn-success" data-toggle="modal"
                                                    data-target="#myModalUpd"  onclick="userUpdShow(${user.userId})">修改</button></td>
                                        <td><button type="button" class="btn btn-danger" onclick="delRole(${user.userId})">删除</button></td>
                                    </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>



                <!-- 模态框（Modal更新） -->
                <div class="modal fade" id="myModalUpd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            </div>
                            <div class="modal-body">
                                <form action="/user/upd" method="post" onsubmit="return smPrepare()">
                                    <input type="hidden" name="userId">
                                    <div class="form-group">
                                        <label for="name">名称</label>
                                        <input type="text" class="form-control" name="username" id="name"
                                               placeholder="请输入名称">
                                    </div>
                                    <div class="form-group">
                                        <label for="name">真实姓名</label>
                                        <input type="text" class="form-control" name="realname" id="realname"
                                               placeholder="请输入名称">
                                    </div>
                                    <div class="form-group">
                                        <label for="name">电话</label>
                                        <input type="text" class="form-control" name="phone" id="phone"
                                               placeholder="请输入描述">
                                    </div>
                               <%--     <div class="form-group">
                                        <label for="name">性别</label>
                                        <input type="radio" class="form-control" name="sex" value="1">男
                                        <input type="radio" class="form-control" name="sex" value="0">女
                                    </div>
                                    <div class="form-group">
                                        <label for="name">是否锁定</label>
                                        <input type="radio" class="form-control" name="locked" value="1">激活
                                        <input type="radio" class="form-control" name="locked" value="0">锁定
                                    </div>--%>
                                    <input id="roleIdsUpd" name="roleIdsUpd" hidden="hidden">
                                    <div>
                                        <h4 class="modal-title" id="myModalLabel">角色列表</h4>
                                        <ul id="treeDemoUpd" class="ztree"></ul>
                                    </div>

                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                        <button type="submit" class="btn btn-primary">提交</button>
                                    </div>
                                </form>
                            </div>

                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </div>
            <!-- content-wrapper ends -->
            <!-- partial:../../partials/_footer.html -->
            <footer class="footer">
                <div class="d-sm-flex justify-content-center justify-content-sm-between">
                    <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright © 2017 Bootstrap Dash. All rights reserved.More Templates <a
                            href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a
                            href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></span>
                    <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted & made with <i
                            class="mdi mdi-heart text-danger"></i></span>
                </div>
            </footer>
            <!-- partial -->
        </div>
        <!-- row-offcanvas ends -->
    </div>
    <!-- page-body-wrapper ends -->
</div>
<!-- container-scroller -->
<!-- plugins:js -->

<script src="../../node_modules/popper.js/dist/umd/popper.min.js"></script>
<script src="../../node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="../../node_modules/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
<!-- endinject -->
<!-- Plugin js for this page-->
<!-- End plugin js for this page-->
<!-- inject:js -->
<script src="../../js/off-canvas.js"></script>
<script src="../../js/misc.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->
<!-- End custom js for this page-->
</body>

</html>


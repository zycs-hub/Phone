# Phone

MainActivity

    完成大部分功能，包含，商场，货架等大部分界面
    跳转到登录等界面
    ThreadLogStatusTest 检测登录状态（没写完）


LogForT

    登录教务处界面，传人参数，if ThreadLogStatusTest 检测到绑定教务处，但教务处密码错误，实现重新绑定
    if 未绑定 清空， 写入数据库（未完成） 跳到选书界面

SelectFromT

    选书界面，重数据库中选择未定义是否要买的书，
        若空，跳到MainActivity
        否则，选着书是否能卖，属性存入数据库（未完成）

further

    不同界面显示不同action bar 货架界面action bar 提供         从教务处重新导入，从教务处导入新增的，导入数据库原来不能卖的，自定义添加
    根据不同登录状态，显示不同界面
    完善数据库
    完善选书部分，添加修改数据库功能

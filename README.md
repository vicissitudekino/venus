#### 一、项目功能

​	项目使用SpringBoot、MybatisPlus、JavaFx、Druid以及Echarts等开发，实现了用户管理、数据预处理、数据查询、数据分析和数据可视化等功能。

#### 二、项目结构

​	项目文件共分为common、service、start和storage四大模块，各模块描述为：

|  Module name  |             Description              |
| :-----------: | :----------------------------------: |
| venus-common  | 提供系统公用的异常处理类以及工具类等 |
| venus-service |         提供操作数据库的接口         |
|  venus-start  |             系统的主服务             |
| venus-storage |      使用SFTP，提供系统存储服务      |

#### 三、项目模式

​	项目使用MVC三层架构模式，将系统业务划分为表现层、业务层以及数据访问层。

<table>
	<tr>
	    <th align="center">Module name</th>
	    <th align="center">Structure name</th>
	    <th align="center">Description</th>  
	</tr >
	<tr >
	    <td rowspan="6" align="center">start</td>
	    <td align="center">constant</td>
	    <td align="center">常量</td>
	</tr>
	<tr>
	    <td align="center">controller</td>
	    <td align="center">业务逻辑层</td>
	</tr>
	<tr>
	    <td align="center">service</td>
	    <td align="center">数据访问层</td>
	</tr>
	<tr>
	    <td align="center">view</td>
	    <td rowspan="3" align="center">表现层</td>
	</tr>
	<tr>
        <td align="center">fxml</td>
	</tr>
	<tr>
	    <td align="center">html</td>
	</tr>
</table>


#### 四、项目运行图
![输入图片说明](https://images.gitee.com/uploads/images/2021/0323/093800_517fde91_5734674.png "11.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0323/094258_d5d4e466_5734674.png "14.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0323/094318_be1332cb_5734674.png "15.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0323/094332_a2d3196d_5734674.png "16.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0323/094346_1d0878a0_5734674.png "164.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0323/094413_26fe343e_5734674.png "13.png")


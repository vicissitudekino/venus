package cn.yansui.module.main.service;

import cn.hutool.core.date.DateUtil;
import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @Description
 * @Author maogen.ymg
 * @Date 2020/3/13 10:07
 */
@Service
@Slf4j
public class MainService {

    /**
     * 设置头像
     * @param headImage 头像控件
     */
    public void setHeadImage(JFXButton headImage){
        headImage.setStyle("-fx-background-image: url(/images/head/default_head.png)");
    }

    /**
     * 设置菜单图标
     * @param iconUrl 图标URL
     * @param menuButton 菜单按钮
     */
    public void setMenuIcon(String iconUrl, JFXButton menuButton){
        Image btnImg = new Image(iconUrl);
        ImageView imageView = new ImageView(btnImg);
        menuButton.setGraphic(imageView);
    }

    /**
     * 根据当前时间获取对应的欢迎语
     * @return 欢迎语
     */
    public String getWelcomeSpeech(){
        int hour = DateUtil.hour(DateUtil.date(),true);
        if(hour >= 6 && hour < 9){
            return "早上好";
        }else if(hour >= 9 && hour < 12){
            return "上午好";
        }else if(hour >= 12 && hour < 14){
            return "中午好";
        }else if(hour >= 14 && hour < 18){
            return "下午好";
        }else if(hour >= 18 && hour < 20){
            return "傍晚好";
        }else if(hour >= 20 && hour < 22){
            return "晚上好";
        }else if(hour >= 22 || hour < 3){
            return "深夜了，注意休息";
        }
        return "";
    }
}

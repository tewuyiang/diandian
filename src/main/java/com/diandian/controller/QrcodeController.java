package com.diandian.controller;

import com.diandian.service.QrcodeService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;

@RestController
@RequestMapping("/qrcode")
public class QrcodeController {

    @Autowired
    private QrcodeService qrcodeService;

    /**
     * 获取房间的二维码
     * @param roomId
     * @param request
     * @throws Exception
     */
    @GetMapping("/showRoomQrcode/{roomId}")
    public void showRoomQrcode(@PathVariable("roomId") Integer roomId,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        // 获取项目根路径
        String realPath = request.getServletContext().getRealPath("/");
        // 查询二维码
        String path = qrcodeService.getRoomQrcode(roomId, realPath);
        // 设置png图片的mimeType
        String mimeType = request.getServletContext().getMimeType("a.png");
        response.setContentType(mimeType);
        // 字节流将文件写回前端
        FileInputStream fis = new FileInputStream(realPath + path);
        IOUtils.copy(fis, response.getOutputStream());
    }

}

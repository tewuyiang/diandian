package com.diandian.service.impl;

import com.diandian.constants.FileConstants;
import com.diandian.dao.QrcodeMapper;
import com.diandian.dao.RoomMapper;
import com.diandian.dao.custom.QrcodeCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Qrcode;
import com.diandian.model.Room;
import com.diandian.service.QrcodeService;
import com.diandian.utils.FileUploadUtils;
import com.diandian.utils.QrcodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class QrcodeServiceImpl implements QrcodeService {

    @Autowired
    private QrcodeMapper qrcodeMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private QrcodeCustomMapper qrcodeCustomMapper;

    /**
     * 创建房间二维码
     * @param contextPath
     * @param room 房间信息
     * @return
     */
    public String createRoomQrode(String contextPath, Room room) throws Exception{
        if (contextPath == null || room == null || room.getId() == null ||
                room.getRoomnumber() == null) {
            throw new ParamException();
        }
        // 文件真实名(以房间+id命名)
        String filename = "room" + room.getRoomnumber() + ".png";
        // 获取文件随机存储位置
        String randomDirectory = FileUploadUtils.getRandomDirectory(filename);
        // 获取文件的uuid
        String uuidName = FileUploadUtils.getUuidName(filename);

        // 创建二维码存储的完整路径
        String savePath = FileConstants.FILE_SAVE_DIR + randomDirectory;
        File file = new File(contextPath, savePath);
        // 二维码存储信息
        String content = FileConstants.QRCODE_MSG + room.getRoomnumber();
        // 生成二维码存入服务器
        String path = QrcodeUtils.createAndSaveQrcode(content, file, uuidName);
        System.out.println(path);

        // 将二维码信息记录进数据库
        Qrcode qrcode = new Qrcode();
        qrcode.setRealname(filename);
        qrcode.setRoomid(room.getId());
        qrcode.setSavepath(savePath);
        qrcode.setUuidname(uuidName);
        qrcodeMapper.insertSelective(qrcode);

        return qrcode.getSavepath() + "/" + uuidName;
    }


    /**
     * 获取房间二维码
     * @param roomId 房间号
     * @param realPath 项目根路径
     * @return
     * @throws Exception
     */
    @Override
    public String getRoomQrcode(Integer roomId, String realPath) throws Exception {
        if (roomId == null || realPath == null) {
            throw new ParamException();
        }
        // 判断房间是否存在
        Room room = roomMapper.selectByPrimaryKey(roomId);
        if (room == null) {
            throw new ParamException();
        }
        // 根据roomid获取二维码的信息
        Qrcode qrcode = qrcodeCustomMapper.selectQrcodeByRoomId(roomId);
        // 判断二维码是否存在
        File file = new File(realPath, qrcode.getSavepath());
        if (file.exists()) {
            return qrcode.getSavepath() + "/" + qrcode.getUuidname();
        }
        // 若房间无二维码，则创建
        return createRoomQrode(realPath, room);
    }

}

package com.ys.mgr.util.http.httpclient;

import com.ys.mgr.util.crypto.MyHashUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 *
 * Date: 2017/12/11 16:12
 */
@Data
@NoArgsConstructor // 自动生成没有参数的构造函数
@AllArgsConstructor // 自动生成全部参数的构造函数
public class MyFileSyncEntity {
    private String fileMd5;
    private File file;
    private String webPath;

    public MyFileSyncEntity(File file, String webPath) {
        this.fileMd5 = MyHashUtils.fileMd5(file);
        this.file = file;
        this.webPath = webPath;
    }
}

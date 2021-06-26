package net.gaven.utildemo.pdf;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 电子合同上的签名信息
 *
 * @author leon
 * @date 2020/9/23
 * @since UCF1.0
 */
@Data
@ToString
@Slf4j
public class SignInfo {

    private String signature;
    private String signerId;
    private String signerNo;
    private String signerName;
    private String timeStamp;
    private String serialNumber;
    private SignerType signerType = SignerType.UNKNOWN;
    private Map extAttrs;
    public enum SignerType{
        ORG,PERSON,UNKNOWN
    }

}



package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 向后返回值的返回码容器
 */
@ApiModel(description = "向后返回值的返回码容器")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen", date = "2019-07-04T10:03:07.723Z")
public class ReturnModel   {
  @JsonProperty("code")
  private String code = null;

  @JsonProperty("errMsg")
  private Object errMsg = null;

  @JsonProperty("data")
  private Object data = null;

  public ReturnModel code(String code) {
    this.code = code;
    return this;
  }

   /**
   * 返回码
   * @return code
  **/
  @ApiModelProperty(value = "返回码")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ReturnModel errMsg(Object errMsg) {
    this.errMsg = errMsg;
    return this;
  }

   /**
   * 附加消息
   * @return errMsg
  **/
  @ApiModelProperty(value = "附加消息")
  public Object getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(Object errMsg) {
    this.errMsg = errMsg;
  }

  public ReturnModel data(Object data) {
    this.data = data;
    return this;
  }

   /**
   * 返回的数据
   * @return data
  **/
  @ApiModelProperty(value = "返回的数据")
  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReturnModel returnModel = (ReturnModel) o;
    return Objects.equals(this.code, returnModel.code) &&
        Objects.equals(this.errMsg, returnModel.errMsg) &&
        Objects.equals(this.data, returnModel.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, errMsg, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReturnModel {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    errMsg: ").append(toIndentedString(errMsg)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


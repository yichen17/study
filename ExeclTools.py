#  这里需要指定 xlrd 的版本号，2.x的版本好像无法读取 .xlsx文件，测试时的版本号是 1.2.0
import xlrd
from xlutils.copy import copy
import requests
import time
import json

url_base="http://localhost:8080/thirdTrans/"
blackList="getBlackList"
fkReport="getFKReport"
checkIdCard="checkIdCard"

class ExcelData():
    def __init__(self,data_path,sheetname):
        self.data_path = data_path
        self.sheetname = sheetname
        self.data = xlrd.open_workbook(self.data_path)
        self.table = self.data.sheet_by_name(self.sheetname)

    def get_blackList(self,mobile,certId):
        print("黑名单写入："+mobile+"|"+certId)
        url=url_base+blackList
        # 'nationCode': 'TH', 'mobile': mobile, 'idCard': certId
        data={}
        data['nationCode']='TH'
        data['mobile']=mobile
        data['idCard']=certId
        headers={'content-type': "application/json"}
        print(url)
        #  此处 如果是用json 方式传输数据的话，要额外设置请求头，即header部分，不然会请求失败
        return requests.post(url=url,data=json.dumps(data),headers=headers).json()
    def get_checkIdCard(self,url,name,certId,code):
        print("身份证验证写入"+name+"|"+certId)
        url=url_base+url+"?nationCode="+code+"&name="+name+"&idCard="+certId
        return requests.get(url).json()
    def get_fkReport(self,mobile,certId):
        print("风控报告写入:"+mobile+"|"+certId)
        url=url_base+fkReport+"?nationCode=TH&dataType=0"+"&mobile="+mobile+"&idCard="+certId
        return requests.get(url).json()

    # excel 中的行列都是从0 开始的
    def readExcel(self):
        L = []
        wb = copy(self.data)  # 利用xlutils.copy下的copy函数复制
        ws = wb.get_sheet(0)  # 获取表单0
        # 不包含 左边界  包含 右边界
        for i in range(4,16):
            sheet_data = {}
            pos=0
            for j in range(0,5):
                sheet_data[pos] = self.table.row_values(i)[j]
                pos=pos+1
            L.append(sheet_data)
            #  写出是如果有中文，泰文等文字时，需要额外设置   ensure_ascii，不然默认是使用 unicode编码
            ws.write(i, 5, json.dumps(self.get_checkIdCard(checkIdCard,self.table.row_values(i)[3],self.table.row_values(i)[4],"TH"),ensure_ascii=False))
            #  设置睡眠时间，考虑到请求会消耗时间
            time.sleep(3)
        #  保存只能保存为 .xls文件，如果保存为 .xlsx文件，会无法打开
        wb.save("result.xls")
        return L



if __name__ == '__main__':
    # 指定 读取的excel 的文件 路径
    data_path = "G:\\pycharm\\program\\test.xlsx"
    # 设置读取的 sheet 的名字
    sheetname = "Sheet1"
    get_data = ExcelData(data_path,sheetname)
    # print(get_data.readExcel())
    print(get_data.get_blackList('0845297696','1102001540393'))




import UIKit

class tab1view: UIViewController {
    
    @IBOutlet weak var pickerview1: UIPickerView!
    
    var pickerlist = ["금천03", "1500고양", "9707", "830파주"]
    var sbusRouteId : String = "117900003";
    
    @IBOutlet weak var tableview1: UITableView!
    
    //총개수
    var nTotalcount : Int = 0
    
    var getArrInfoByRouteAllDataList : [getArrInfoByRouteAllData] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.view.backgroundColor = .white
        
        initPickerView()
        
        initTableview()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        self.getArrInfoByRouteAllDataList.removeAll()
    }
    
    private func initPickerView(){
        
        pickerview1.backgroundColor = UIColor.white
        
        pickerview1.delegate = self
        pickerview1.dataSource = self
    }
    
    private func initTableview(){
        
        tableview1.delegate = self
        tableview1.dataSource = self
        
        tableview1.backgroundColor = .white
        tableview1.separatorStyle = .none
        
        let nibName = UINib(nibName: "item1Cell", bundle: nil)
        tableview1.register(nibName, forCellReuseIdentifier: "item1Cell")
    }
    
    @IBAction func btnSearch_click(_ sender: ButtonEx) {
        
        if ( sbusRouteId.isEmpty ) {
            return
        }
        
        getArrInfomainAPI.shared.getData(sbusRouteId: sbusRouteId) { (result) in
            
            switch result{
            case .success(let getArrInfoByRouteAllData):
                
                self.getArrInfoByRouteAllDataList.removeAll()
                self.getArrInfoByRouteAllDataList = getArrInfoByRouteAllData
                
                self.nTotalcount = self.getArrInfoByRouteAllDataList.count
                
                //변경된 내용 반영
                self.tableview1.reloadData()
                
                break
            case .failure(let error):
                print(error)
                break
            }
        }
    }
}


extension tab1view : UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableview1.dequeueReusableCell(withIdentifier: "item1Cell", for: indexPath) as! item1Cell
        
        //cell 클릭 효과 무시
        cell.selectionStyle = .none
        
        let arrmsg1 : String = self.getArrInfoByRouteAllDataList[indexPath.row].arrmsg1!
        let arrmsg2 : String = self.getArrInfoByRouteAllDataList[indexPath.row].arrmsg2!
        let arsId : String = self.getArrInfoByRouteAllDataList[indexPath.row].arsId!
        let rtNm : String = self.getArrInfoByRouteAllDataList[indexPath.row].rtNm!
        let stId : String = self.getArrInfoByRouteAllDataList[indexPath.row].stId!
        let stNm : String = self.getArrInfoByRouteAllDataList[indexPath.row].stNm!
        
        cell.label2.text = arrmsg1
        cell.label4.text = arrmsg2
        cell.label6.text = arsId
        cell.label8.text = rtNm
        cell.label10.text = stId
        cell.label12.text = stNm
     
        return cell
    }
    
    //전체 데이터 개수
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return nTotalcount
    }
    
    //섹션수
    func numberOfSections(in tableView: UITableView) -> Int {
        
        return 1
    }
    
    //상단 공간
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        
        return 5.0
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let head = UIView()
        head.backgroundColor = UIColor.white
        return head
    }
    
    //하단 공간
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        
        return 5.0
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        
        let head = UIView()
        head.backgroundColor = UIColor.white
        return head
    }
    
    //선택셀
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        tableview1.deselectRow(at: indexPath, animated: true)
    }
}

extension tab1view : UIPickerViewDelegate, UIPickerViewDataSource {
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        
        var pickerLabel : UILabel? = (view as? UILabel)
        if pickerLabel == nil {
            
            pickerLabel = UILabel()
            pickerLabel?.font = UIFont.systemFont(ofSize: 14)
            pickerLabel?.textAlignment = .center
            pickerLabel?.adjustsFontSizeToFitWidth = true
            pickerLabel?.minimumScaleFactor = 0.8
        }
        
        var textColor : UIColor!
        if pickerView.selectedRow(inComponent: component) == row {
            textColor = UIColor.orange
        } else {
            textColor = UIColor.gray
        }
        
        var attributeStr = NSAttributedString()
        attributeStr = NSAttributedString(string: pickerlist[row], attributes: [NSAttributedString.Key.foregroundColor : textColor])
        pickerLabel?.attributedText = attributeStr
        
        pickerLabel?.text = pickerlist[row]
        
        return pickerLabel!
    }
    
    //컴포넌트 수
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    //데이터 개수
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        return pickerlist.count
    }
    
    //제목 설정
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        return pickerlist[row]
    }
    
    //선택된 경우
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        var selectValue : String = pickerlist[row]
        
        if selectValue == "금천03" {
            
            sbusRouteId = "117900003"
            
        } else if selectValue == "1500고양" {
            
            sbusRouteId = "218000010"
            
        } else if selectValue == "9707" {
            
            sbusRouteId = "100100400"
            
        } else if selectValue == "9707" {
            
            sbusRouteId = "229000017"
        }
        
        pickerView.reloadAllComponents()
    }
}

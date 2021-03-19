
import UIKit

class tab4view: UIViewController {
    
    @IBOutlet weak var textfield: TextFieldEx!
    @IBOutlet weak var tableview1: UITableView!
    
    //총개수
    var nTotalcount : Int = 0
    
    var getBusRouteListDataList : [getBusRouteListData] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.view.backgroundColor = .white
        
        initTextFieldEx()
        
        initTableview()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        self.getBusRouteListDataList.removeAll()
    }
    
    private func initTextFieldEx(){
        
        textfield.keyboardType = .numbersAndPunctuation
        
        textfield.delegate = self
    }
    
    private func initTableview(){
        
        tableview1.delegate = self
        tableview1.dataSource = self
        
        tableview1.backgroundColor = .white
        tableview1.separatorStyle = .none
        
        let nibName = UINib(nibName: "item4Cell", bundle: nil)
        tableview1.register(nibName, forCellReuseIdentifier: "item4Cell")
    }
    
    
    @IBAction func btnSearch_click(_ sender: ButtonEx) {
        
        guard let busNum = self.textfield.text else { return }
        
        if ( busNum.isEmpty ) {
            return
        }
        
        getBusRouteListAPI.shared.getData(sNum: busNum) { (result) in
            switch result{
            case .success(let getBusRouteListData):
                
                self.getBusRouteListDataList.removeAll()
                self.getBusRouteListDataList = getBusRouteListData
                
                self.nTotalcount = self.getBusRouteListDataList.count
                
                //변경된 내용 반영
                self.tableview1.reloadData()
               
                break
            case .failure(let error):
                print(error)
                break
            }
        }
        
        self.textfield.resignFirstResponder()
        self.textfield.text = ""
    }
}

extension tab4view : cellDelegate {
    
    func didClickImageView(cell : UITableViewCell){
        
        let indexPath = self.tableview1.indexPath(for: cell)
        
        if let index = indexPath?.row {
            
            let busRouteId : String = self.getBusRouteListDataList[index].busRouteId!
            
            if busRouteId != "" {
                UIPasteboard.general.string = busRouteId
                //print(UIPasteboard.general.string!)
            }
        }
    }
    
    func didClickroundbackground(cell : UITableViewCell){
        
        let indexPath = self.tableview1.indexPath(for: cell)
        
        if let index = indexPath?.row {
            
            let busRouteId : String = self.getBusRouteListDataList[index].busRouteId!
            
            if busRouteId != "" {
                UIPasteboard.general.string = busRouteId
                //print(UIPasteboard.general.string!)
                
            }
        }
    }
}

extension tab4view : UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableview1.dequeueReusableCell(withIdentifier: "item4Cell", for: indexPath) as! item4Cell
        
        //cell 클릭 효과 무시
        cell.selectionStyle = .none
        cell.delegate = self
        
        let busRouteId : String = self.getBusRouteListDataList[indexPath.row].busRouteId!
        let busRouteNm : String = self.getBusRouteListDataList[indexPath.row].busRouteNm!
        let edStationNm : String = self.getBusRouteListDataList[indexPath.row].edStationNm!
        let stStationNm : String = self.getBusRouteListDataList[indexPath.row].stStationNm!
        let term : String = self.getBusRouteListDataList[indexPath.row].term!
        
        cell.label2.text = busRouteId
        cell.label4.text = busRouteNm
        cell.label6.text = edStationNm
        cell.label8.text = stStationNm
        cell.label10.text = term
     
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

extension tab4view : UITextFieldDelegate {
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        
        //print("텍스트 입력 상태를 시작해도 되는지 물음")
        
        return true
    }
    
    func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        
        //print("텍스트 입력 상태를 종료해도 되는지 물음")
        
        return true
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        
        //print("textfield 입력시작")
        
        self.textfield.setborderColor(bFocus : true)
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        
        //print("textfield 입력종료")
        
        self.textfield.setborderColor(bFocus : false)
    }
    
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        
        //print("텍스트 입력값이 변경 될때 호출 \(string)")
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        //print("textfield 리턴키 클릭시\(textField.tag)")
        
        textField.resignFirstResponder()
        
        return true
    }
}


import UIKit
import SideMenu

class ViewController: UIViewController {
    
    var sideMenu : SideMenuNavigationController!
    
    @IBOutlet weak var toolbar: UIToolbar!
    @IBOutlet weak var tabmenuview: UICollectionView!
    @IBOutlet weak var contentview: UIView!
    
    var tabmenuName: [String] = ["나의경로", "버스조회", "즐겨찾기", "버스번호"]
    var selectIndex : Int = 0
    var currentVC : UIViewController!
    
    var swipeLeft : UISwipeGestureRecognizer!
    var swipeRight : UISwipeGestureRecognizer!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initToolbar()
        
        initTabMenu()
        
        initswipe()
        
        initsideMenu()
    }
    
    private func initToolbar(){
        
        toolbar.isTranslucent = true //(true:반투명, false:투명)
        toolbar.backgroundColor = UIColor(red: 27.0/255, green: 40.0/255, blue: 54.0/255, alpha: 1.0)
        toolbar.barTintColor = UIColor(red: 27.0/255, green: 40.0/255, blue: 54.0/255, alpha: 1.0)
        toolbar.tintColor = UIColor.white
        toolbar.sizeToFit()
    }
    
    private func initTabMenu(){
        
        tabmenuview.delegate = self
        tabmenuview.dataSource = self
        
        tabmenuview.isScrollEnabled = false
        
        tabmenuview.backgroundColor = UIColor(red: 27.0/255, green: 40.0/255, blue: 54.0/255, alpha: 1.0)
        
        SelecttabIndex(selectIndex: selectIndex)
    }
    
    private func initswipe(){
        
        swipeLeft = UISwipeGestureRecognizer(target: self, action: #selector(swipeGesture(_:)))
        swipeLeft.direction = UISwipeGestureRecognizer.Direction.left
        swipeRight = UISwipeGestureRecognizer(target: self, action: #selector(swipeGesture(_:)))
        swipeRight.direction = UISwipeGestureRecognizer.Direction.right
       
        self.contentview.addGestureRecognizer(swipeLeft)
        self.contentview.addGestureRecognizer(swipeRight)
    }
    
    private func GetUIViewController(item : String) {
        
        if currentVC != nil {
            currentVC.willMove(toParent: nil)
            currentVC.view.removeFromSuperview()
            currentVC.removeFromParent()
        }
        
        currentVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: item)
        
        self.addChild(currentVC)
        currentVC.view.frame = contentview.bounds
        contentview.addSubview(currentVC.view)
        currentVC.didMove(toParent: self)
    }
    
    private func SelecttabIndex(selectIndex : Int) {
        
        switch selectIndex {
        
        case 0:
            GetUIViewController(item: "tab1view")
            
        case 1:
            GetUIViewController(item: "tab2view")
            
        case 2:
            GetUIViewController(item: "tab3view")
            
        case 3:
            GetUIViewController(item: "tab4view")
            
        default:
            break
        }
    }
    
    private func initsideMenu() {
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "menuViewController") as! menuViewController
        vc.delegate = self
        
        sideMenu = SideMenuNavigationController(rootViewController: vc)
        sideMenu.leftSide = true
        sideMenu.sideMenuDelegate = self
        sideMenu.menuWidth = self.view.bounds.width * 0.8
        sideMenu.presentationStyle = .menuSlideIn //이게 없으면 메뉴 버튼 오른쪽을 밀려남
        SideMenuManager.default.leftMenuNavigationController = sideMenu
        SideMenuManager.default.leftMenuNavigationController?.setNavigationBarHidden(true, animated: true)//상단 상태바 숨김
    }
    
    @IBAction func menu_click(_ sender: UIBarButtonItem) {
        present(sideMenu, animated: true)
    }
    
    func changetab(index : Int){
        
        //현재탭
        tabmenuview.reloadItems(at: [IndexPath(item: selectIndex, section: 0)])
        
        //선택탭
        selectIndex = index
        tabmenuview.reloadItems(at: [IndexPath(item: selectIndex, section: 0)])
        
        SelecttabIndex(selectIndex: selectIndex)
    }
}

extension ViewController : UICollectionViewDelegate {
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        collectionView.deselectItem(at: indexPath, animated: true)
       
        //현재탭
        tabmenuview.reloadItems(at: [IndexPath(item: selectIndex, section: 0)])
        
        //선택탭
        selectIndex = indexPath.row
        tabmenuview.reloadItems(at: [IndexPath(item: selectIndex, section: 0)])
        
        SelecttabIndex(selectIndex: selectIndex)
    }
}

extension ViewController: UICollectionViewDataSource{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        return tabmenuName.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "tabCell", for: indexPath) as! tabCell

        cell.namelabel.text = tabmenuName[indexPath.row]
        cell.namelabel.textColor = UIColor.grayColor()
        
        cell.lineview.backgroundColor = .clear
        
        if indexPath.item == selectIndex {
            cell.isSelected = true
            collectionView.selectItem(at: indexPath, animated: false, scrollPosition: .init())
        }
        
        return cell
    }
    
    @objc func swipeGesture(_ sender: UISwipeGestureRecognizer){
        
        if sender.direction == .left{
            
            if selectIndex+1 >= tabmenuName.count{
                return
            }
            selectIndex += 1
            
            tabmenuview.reloadItems(at: [IndexPath(item: selectIndex, section: 0)])
            tabmenuview.reloadItems(at: [IndexPath(item: selectIndex - 1, section: 0)])
            
            SelecttabIndex(selectIndex: selectIndex)
        }
        
        if sender.direction == .right{
            
            if selectIndex - 1 < 0{
                return
            }
            selectIndex -= 1
            
            tabmenuview.reloadItems(at: [IndexPath(item: selectIndex, section: 0)])
            tabmenuview.reloadItems(at: [IndexPath(item: selectIndex + 1, section: 0)])
            
            SelecttabIndex(selectIndex: selectIndex)
        }
    }
    
}

extension ViewController: UICollectionViewDelegateFlowLayout{
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let width = collectionView.bounds.width/4
        let height = 50
        return CGSize(width: Int(width), height: height)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        
        return 0.5
    }
}

extension ViewController : MenuViewControllerDelegate {
    
    func didselectedMenu(index : Int){
        
        sideMenu.dismiss(animated: true, completion: nil)
        
        //첫번째 탭으로 이동
        changetab(index: 0)
    }
}

extension ViewController: SideMenuNavigationControllerDelegate {

    func sideMenuWillAppear(menu: SideMenuNavigationController, animated: Bool) {
        //print("sideMenuWillAppear")
    }

    func sideMenuDidAppear(menu: SideMenuNavigationController, animated: Bool) {
        //print("sideMenuDidAppear")
    }

    func sideMenuWillDisappear(menu: SideMenuNavigationController, animated: Bool) {
        //print("sideMenuWillDisappear")
    }

    func sideMenuDidDisappear(menu: SideMenuNavigationController, animated: Bool) {
        //print("sideMenuDidDisappear")
    }
}

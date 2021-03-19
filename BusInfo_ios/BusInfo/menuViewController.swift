import UIKit

protocol MenuViewControllerDelegate {
    func didselectedMenu(index : Int)
}

class menuViewController: UIViewController {

    public var delegate : MenuViewControllerDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func menu1_click(_ sender: Any) {
        
        delegate?.didselectedMenu(index: 1)
    }
   
}

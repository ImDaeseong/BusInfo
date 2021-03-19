import UIKit

class ButtonEx : UIButton {
     
    @IBInspectable var txtTitle : String = "" {
        didSet {
            self.setTitle(txtTitle, for: .normal)
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        initButton()
    }
    
    required override init(frame: CGRect) {
        super.init(frame: frame)
        initButton()
    }
    
    private func initButton(){
        
        self.setTitle("", for: .normal)
        
        self.layer.borderColor = UIColor.orangeColor().cgColor
        self.layer.borderWidth = 1.0
        self.layer.cornerRadius = 5
        self.backgroundColor = UIColor.orangeColor()
        
        if state == .normal {
           
            setTitleColor(UIColor.btnTextColor(), for: UIControl.State.normal)
            
        } else if state == .selected {
            
            setTitleColor(UIColor.white, for: UIControl.State.selected)
        }
    }
    
}

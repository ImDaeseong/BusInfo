
import UIKit

class TextFieldEx: UITextField {
       
    let border = CALayer()
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        initTextField()
    }
    
    required override init(frame: CGRect) {
        super.init(frame: frame)
        initTextField()
    }
    
    private func initTextField(){
        
        self.font = UIFont.systemFont(ofSize: 14)
        self.textColor = UIColor.black
        self.textAlignment = .left
        self.tintColor = UIColor.orangeColor()//포커스 색상
        
        //placeholder 텍스트 색상
        self.attributedPlaceholder = NSAttributedString(string: self.placeholder != nil ? self.placeholder! : "", attributes: [NSAttributedString.Key.foregroundColor : UIColor.textfieldgrayColor()])
        
        //let border = CALayer()
        let linewidth = CGFloat(0.5)
        
        border.borderColor = UIColor.textfieldgrayColor().cgColor
        border.frame = CGRect(x: 0, y: self.frame.size.height - linewidth, width: self.frame.size.width, height: self.frame.size.height)
        border.borderWidth = linewidth
        
        self.layer.addSublayer(border)
        self.borderStyle = .none
        self.layer.masksToBounds = true
    }
    
    public func setborderColor(bFocus : Bool){
        
        if bFocus {
            
            border.borderColor = UIColor.orangeColor().cgColor
        } else {
            
            border.borderColor = UIColor.textfieldgrayColor().cgColor
        }
    }
}

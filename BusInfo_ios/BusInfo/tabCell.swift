import UIKit

class tabCell: UICollectionViewCell {
    
    @IBOutlet weak var namelabel: UILabel!
    @IBOutlet weak var lineview: UIView!
    
    override var isSelected: Bool {
        didSet {
            
            self.namelabel.textColor = isSelected ? .white : UIColor.grayColor()
            self.lineview.backgroundColor = isSelected ? UIColor.orangeColor() : .clear
            
        }
    }
}

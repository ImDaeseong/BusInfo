
import UIKit

protocol cellDelegate {
    func didClickImageView(cell : UITableViewCell)
    func didClickroundbackground(cell : UITableViewCell)
}

class item4Cell: UITableViewCell {
    
    var delegate : cellDelegate?

    @IBOutlet weak var label1: LabelEx!
    @IBOutlet weak var label2: LabelEx!
    @IBOutlet weak var label3: LabelEx!
    @IBOutlet weak var label4: LabelEx!
    @IBOutlet weak var label5: LabelEx!
    @IBOutlet weak var label6: LabelEx!
    @IBOutlet weak var label7: LabelEx!
    @IBOutlet weak var label8: LabelEx!
    @IBOutlet weak var label9: LabelEx!
    @IBOutlet weak var label10: LabelEx!
    @IBOutlet weak var imageview1: UIImageView!
    @IBOutlet weak var roundbackground: roundbackground!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        let tapGesture1 = UITapGestureRecognizer(target: self, action: #selector(imgview1Tapped))
        imageview1.addGestureRecognizer(tapGesture1)
        imageview1.isUserInteractionEnabled = true
        
        let tapGesture2 = UITapGestureRecognizer(target: self, action: #selector(roundbackgroundTapped))
        roundbackground.addGestureRecognizer(tapGesture2)
        roundbackground.isUserInteractionEnabled = true
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    @IBAction func imgview1Tapped(_ sender : Any){
        
        //print("imgview1Tapped")
        
        delegate?.didClickImageView(cell: self)
    }
    
    @IBAction func roundbackgroundTapped(_ sender : Any){
        
        //print("roundbackgroundTapped")
        
        delegate?.didClickroundbackground(cell: self)
    }
    
}

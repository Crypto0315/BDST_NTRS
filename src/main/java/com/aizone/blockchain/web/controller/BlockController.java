package com.aizone.blockchain.web.controller;

import com.aizone.blockchain.conf.Settings;
import com.aizone.blockchain.core.Block;
import com.aizone.blockchain.core.BlockChain;
import com.aizone.blockchain.core.Transaction;
import com.aizone.blockchain.db.DBAccess;
import com.aizone.blockchain.net.base.Node;
import com.aizone.blockchain.utils.JsonVo;
import com.aizone.blockchain.web.vo.TransactionVo;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *  
 * @since 24-6-6 上午10:50.
 */
@RestController
@RequestMapping("/chain")
public class BlockController {
    /**
     * 收款人私钥，测试数据
     */
    private static final String SENDER_PRIVATE_KEY = "";
    /**
     * 收款人公钥，测试数据
     */
    private static final String SENDER_PUBLIC_KEY = "";
    @Autowired
    private DBAccess dbAccess;

    @Autowired
    private BlockChain blockChain;
    @Autowired
    private Settings settings;

    @GetMapping({"", "/", "index"})
    public JsonVo index(HttpServletRequest request) {
        return JsonVo.success();
    }

    /**
     * 挖矿
     * @param request
     * @return
     */
    @PostMapping("/mine")
    public JsonVo mine(HttpServletRequest request) throws Exception {

        Block block = blockChain.mining();
        JsonVo vo = new JsonVo();
        vo.setCode(JsonVo.CODE_SUCCESS);
        vo.setMessage("Create a new block");
        vo.setItem(block);
        return vo;
    }

    /**
     * 浏览区块
     * @param request
     * @return
     */
    @PostMapping("/block/view")
    public JsonVo viewChain(HttpServletRequest request) {

        Optional<Block> block = dbAccess.getLastBlock();
        JsonVo success = JsonVo.success();
        if (block.isPresent()) {
            success.setItem(block.get());
        }
        return success;

    }

    /**
     * 发送交易
     * @param txVo
     * @return
     */
    @PostMapping("/transactions/new")
    public JsonVo sendTransaction(@RequestBody TransactionVo txVo) throws Exception {
        Preconditions.checkNotNull(txVo.getSender(), "Sender is needed.");
        Preconditions.checkNotNull(txVo.getRecipient(), "Recipient is needed.");
        Preconditions.checkNotNull(txVo.getAmount(), "Amount is needed.");
        Preconditions.checkNotNull(txVo.getPrivateKey(), "Private Key is needed.");
        Transaction tx = new Transaction();
        BeanUtils.copyProperties(txVo, tx);
        Transaction transaction = blockChain.sendTransaction(tx, txVo.getPrivateKey());
        //如果开启了自动挖矿，则直接自动挖矿
        if (settings.isAutoMining()) {
            blockChain.mining();
        }
        JsonVo success = JsonVo.success();
        Transaction transaction1 = new Transaction();
        transaction1.setSender(transaction.getSender());
        transaction1.setRecipient(transaction.getRecipient());
        transaction1.setPublicKey(transaction.getPublicKey());
        transaction1.setAmount(transaction.getAmount());
        transaction1.setTimestamp(transaction.getTimestamp());
        transaction1.setTxHash(transaction.getTxHash());
        transaction1.setData(transaction.getData());
        success.setItem(transaction1);
        return success;
    }

    /**
     * 添加节点
     * @param node
     * @return
     * @throws Exception
     */
    @PostMapping("/node/add")
    public JsonVo addNode(@RequestBody Map<String, Object> node) throws Exception {

        Preconditions.checkNotNull(node.get("ip"), "server ip is needed.");
        Preconditions.checkNotNull(node.get("port"), "server port is need.");

        blockChain.addNode(String.valueOf(node.get("ip")), (Integer) node.get("port"));
        return JsonVo.success();
    }

    /**
     * 查看节点列表
     * @param request
     * @return
     */
    @PostMapping("node/view")
    public JsonVo nodeList(HttpServletRequest request) {

        Optional<List<Node>> nodeList = dbAccess.getNodeList();
        JsonVo success = JsonVo.success();
        if (nodeList.isPresent()) {
            success.setItem(nodeList.get());
        }
        return success;
    }

}

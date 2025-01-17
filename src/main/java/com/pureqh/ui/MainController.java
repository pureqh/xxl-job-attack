package com.pureqh.ui;

/**
 * @Author：index
 * @Date：2024/7/17 15:06
 */


import java.io.IOException;
import java.net.*;
import java.net.Proxy.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

import cn.hutool.core.util.RandomUtil;
import com.pureqh.attack.AttackService;
import com.pureqh.util.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class MainController {

    @FXML
    private MenuItem proxySetupBtn;
    @FXML
    private TextField targetAddress;
    @FXML
    private TextField apiPath;
    @FXML
    private TextField httpTimeout;
    @FXML
    public ComboBox<String> vulOpt;
    @FXML
    public TextArea logTextArea;
    @FXML
    private Label proxyStatusLabel;
    public static Map currentProxy = new HashMap();

    @FXML
    private TextField exCommandText;
    @FXML
    public TextArea execOutputArea;

    @FXML
    public TextArea InjOutputArea ;


    public MainController() {


    }



    @FXML
    void injectShellBtn(ActionEvent event) {
        InjOutputArea.setText("");
        if (!vulOpt.getValue().equals("api接口未授权Hessian反序列化")){
            InjOutputArea.appendText(Utils.log("[info] 请选择api接口未授权Hessian反序列化模块\n"));
        }else {

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    AttackService attackService = new AttackService();
                    String code = attackService.injectShellAttack(targetAddress.getText(), httpTimeout.getText(), apiPath.getText());
                    if (code != "error") {
                        InjOutputArea.appendText(Utils.log(code + "\n"));
                    } else {
                        InjOutputArea.appendText(Utils.log("[-] 内存马注入失败\n"));
                    }
                }

            });
            thread.start();

        }

    }

    @FXML
    void vulOptBtn(ActionEvent event) {


        logTextArea.setText("");
        logTextArea.appendText(Utils.log("[info] 开始扫描\n"));
        AttackService attackService = new AttackService();


        String code = attackService.TestRequest(targetAddress.getText(),httpTimeout.getText());

        if (code.equals("success")){
            logTextArea.appendText(Utils.log("[info] 网站存活！！\n"));
        }else{
            logTextArea.appendText(Utils.log("[error] 请求超时,请检查URL是否可以访问\n"));
            return;

        }


        if (vulOpt.getValue().equals("All")){

            String code1 = attackService.passIntruderAttack(targetAddress.getText(),httpTimeout.getText());
            if (code1.equals("success")){
                logTextArea.appendText(Utils.log("[+] 默认口令为admin 123456\n"));


            }else{
                logTextArea.appendText(Utils.log("[info] 默认口令扫描结束\n"));
            }


            String code2 = attackService.apiScanAttack(targetAddress.getText(),httpTimeout.getText());
            if (code2!="error"){
                logTextArea.appendText(Utils.log("[+] 存在api接口"+code2+"，请移步内存马模块尝试攻击\n"));
            }else{
                logTextArea.appendText(Utils.log("[info] api接口扫描结束\n"));
            }


            String code3 = attackService.runApiUnauthorizedAttack(targetAddress.getText(),httpTimeout.getText(),exCommandText.getText());
            if (code3!="error"){
                logTextArea.appendText(Utils.log("[+] 存在Executor未授权命令执行，请移步命令执行模块\n"));
            }else{
                logTextArea.appendText(Utils.log("[info] Executor未授权命令执行扫描结束\n"));
            }


            String code4 = attackService.defaultTokenAttack(targetAddress.getText(),httpTimeout.getText(),exCommandText.getText());
            if (code4!="error"){
                logTextArea.appendText(Utils.log("[+] 存在默认accessToken身份绕过，请移步命令执行模块\n"));
            }else{
                logTextArea.appendText(Utils.log("[info] 默认accessToken身份绕过扫描结束\n"));
            }


        }
        else if (vulOpt.getValue().equals("默认口令")) {

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    String code = attackService.passIntruderAttack(targetAddress.getText(),httpTimeout.getText());
                    if (code.equals("success")){
                        logTextArea.appendText(Utils.log("[+] 默认口令为admin 123456\n"));
                    }else{
                        logTextArea.appendText(Utils.log("[info] 默认口令扫描结束\n"));
                    }
                }

            });
            thread.start();
            return;

        }else if (vulOpt.getValue().equals("api接口未授权Hessian反序列化")){
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    String code = attackService.apiScanAttack(targetAddress.getText(),httpTimeout.getText());
                    if (code!="error"){
                        logTextArea.appendText(Utils.log("[+] 存在api接口"+code+"，请移步内存马模块尝试攻击\n"));
                    }else{
                        logTextArea.appendText(Utils.log("[info] api接口扫描结束\n"));
                    }
                }

            });
            thread.start();
            return;
        }else if (vulOpt.getValue().equals("Executor未授权命令执行")){
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    String code = attackService.runApiUnauthorizedAttack(targetAddress.getText(),httpTimeout.getText(),exCommandText.getText());
                    if (code!="error"){
                        logTextArea.appendText(Utils.log("[+] 存在Executor未授权命令执行，请移步命令执行模块\n"));
                    }else{
                        logTextArea.appendText(Utils.log("[info] Executor未授权命令执行扫描结束\n"));
                    }
                }

            });
            thread.start();

            return;
        }else if (vulOpt.getValue().equals("默认accessToken身份绕过")){
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    String code = attackService.defaultTokenAttack(targetAddress.getText(),httpTimeout.getText(),exCommandText.getText());
                    if (code!="error"){
                        logTextArea.appendText(Utils.log("[+] 存在默认accessToken身份绕过，请移步命令执行模块\n"));
                    }else{
                        logTextArea.appendText(Utils.log("[info] 默认accessToken身份绕过扫描结束\n"));
                    }
                }

            });
            thread.start();
            return;
        }


    }
    @FXML
    void executeCmdBtn(ActionEvent event) {

        execOutputArea.setText("");

        if (vulOpt.getValue().equals("All")){
            execOutputArea.appendText(Utils.log("[info] 请选择Executor未授权命令执行或默认accessToken身份绕过模块\n"));
        }else if(vulOpt.getValue().equals("默认口令")) {
            execOutputArea.appendText(Utils.log("[info] 请选择Executor未授权命令执行或默认accessToken身份绕过模块\n"));

        }else if(vulOpt.getValue().equals("api接口未授权Hessian反序列化")){
            execOutputArea.appendText(Utils.log("[info] 请选择Executor未授权命令执行或默认accessToken身份绕过模块\n"));

        }
        else  {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    AttackService attackService = new AttackService();
                    String code = attackService.defaultTokenAttack(targetAddress.getText(), httpTimeout.getText(), exCommandText.getText());
                    if (code != "error") {
                        execOutputArea.appendText(Utils.log("[+] 命令执行成功，请自行检查\n"));
                    } else {
                        execOutputArea.appendText(Utils.log("[-] 命令执行失败\n"));
                    }
                }

            });
            thread.start();
        }

    }

    @FXML
    void initialize() {
        this.initToolbar();
        this.initComBoBox();
        logTextArea.setText("[info] 命令执行只实现了Executor未授权命令执行和默认accessToken身份绕过。\n" +
                "[info] 内存马使用了xslt，由于测试直接打入内存马有时会失败，所以选择直接打入agent内存马，\n" +
                "如需自定义可替换resources下的ser文件，其中agent.ser为agent内存马、xslt.ser会落地为/tmp/2.xslt,\n" +
                "内容为使用exec执行/tmp/agent.jar、exp.ser则是加载/tmp/2.xslt。");


    }

    public void initComBoBox() {

        ObservableList<String> vulOpts = FXCollections.observableArrayList(new String[]{ "All","默认口令","api接口未授权Hessian反序列化", "Executor未授权命令执行", "默认accessToken身份绕过",});
        this.vulOpt.setPromptText("All");
        this.vulOpt.setValue("All");
        this.vulOpt.setItems(vulOpts);

    }
    private void initToolbar() {
        this.proxySetupBtn.setOnAction((event) -> {
            Alert inputDialog = new Alert(AlertType.NONE);
            inputDialog.setResizable(true);
            Window window = inputDialog.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest((e) -> {
                window.hide();
            });
            ToggleGroup statusGroup = new ToggleGroup();
            RadioButton enableRadio = new RadioButton("启用");
            RadioButton disableRadio = new RadioButton("禁用");
            enableRadio.setToggleGroup(statusGroup);
            disableRadio.setToggleGroup(statusGroup);
            HBox statusHbox = new HBox();
            statusHbox.setSpacing(10.0D);
            statusHbox.getChildren().add(enableRadio);
            statusHbox.getChildren().add(disableRadio);
            GridPane proxyGridPane = new GridPane();
            proxyGridPane.setVgap(15.0D);
            proxyGridPane.setPadding(new Insets(20.0D, 20.0D, 0.0D, 10.0D));
            Label typeLabel = new Label("类型：");
            ComboBox<String> typeCombo = new ComboBox();
            typeCombo.setItems(FXCollections.observableArrayList(new String[]{"HTTP", "SOCKS"}));
            typeCombo.getSelectionModel().select(0);
            Label IPLabel = new Label("IP地址：");
            TextField IPText = new TextField();
            IPText.setText("127.0.0.1");
            Label PortLabel = new Label("端口：");
            TextField PortText = new TextField();
            PortText.setText("8080");
            Label userNameLabel = new Label("用户名：");
            TextField userNameText = new TextField();
            Label passwordLabel = new Label("密码：");
            TextField passwordText = new TextField();
            Button cancelBtn = new Button("取消");
            Button saveBtn = new Button("保存");
            saveBtn.setDefaultButton(true);
            if (currentProxy.get("proxy") != null) {
                Proxy currProxy = (Proxy)currentProxy.get("proxy");
                String proxyInfo = currProxy.address().toString();
                String[] info = proxyInfo.split(":");
                String hisIpAddress = info[0].replace("/", "");
                String hisPort = info[1];
                IPText.setText(hisIpAddress);
                PortText.setText(hisPort);
                enableRadio.setSelected(true);
                System.out.println(proxyInfo);
            } else {
                enableRadio.setSelected(false);
            }

            saveBtn.setOnAction((e) -> {
                if (disableRadio.isSelected()) {
                    currentProxy.put("proxy", (Object)null);
                    this.proxyStatusLabel.setText("");
                    inputDialog.getDialogPane().getScene().getWindow().hide();
                } else {
                    String type;
                    String ipAddress;
                    if (!userNameText.getText().trim().equals("")) {
                        ipAddress = userNameText.getText().trim();
                        type = passwordText.getText();
                        String finalIpAddress = ipAddress;
                        String finalType = type;
                        Authenticator.setDefault(new Authenticator() {
                            @Override
                            public PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(finalIpAddress, finalType.toCharArray());
                            }
                        });
                    } else {
                        Authenticator.setDefault((Authenticator)null);
                    }

                    currentProxy.put("username", userNameText.getText());
                    currentProxy.put("password", passwordText.getText());
                    ipAddress = IPText.getText();
                    String port = PortText.getText();
                    InetSocketAddress proxyAddr = new InetSocketAddress(ipAddress, Integer.parseInt(port));
                    type = ((String)typeCombo.getValue()).toString();
                    Proxy proxy;
                    if (type.equals("HTTP")) {
                        proxy = new Proxy(Type.HTTP, proxyAddr);
                        currentProxy.put("proxy", proxy);
                    } else if (type.equals("SOCKS")) {
                        proxy = new Proxy(Type.SOCKS, proxyAddr);
                        currentProxy.put("proxy", proxy);
                    }

                    this.proxyStatusLabel.setText("代理生效中: " + ipAddress + ":" + port);
                    inputDialog.getDialogPane().getScene().getWindow().hide();
                }

            });
            cancelBtn.setOnAction((e) -> {
                inputDialog.getDialogPane().getScene().getWindow().hide();
            });
            proxyGridPane.add(statusHbox, 1, 0);
            proxyGridPane.add(typeLabel, 0, 1);
            proxyGridPane.add(typeCombo, 1, 1);
            proxyGridPane.add(IPLabel, 0, 2);
            proxyGridPane.add(IPText, 1, 2);
            proxyGridPane.add(PortLabel, 0, 3);
            proxyGridPane.add(PortText, 1, 3);
            proxyGridPane.add(userNameLabel, 0, 4);
            proxyGridPane.add(userNameText, 1, 4);
            proxyGridPane.add(passwordLabel, 0, 5);
            proxyGridPane.add(passwordText, 1, 5);
            HBox buttonBox = new HBox();
            buttonBox.setSpacing(20.0D);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().add(cancelBtn);
            buttonBox.getChildren().add(saveBtn);
            GridPane.setColumnSpan(buttonBox, 2);
            proxyGridPane.add(buttonBox, 0, 6);
            inputDialog.getDialogPane().setContent(proxyGridPane);
            inputDialog.showAndWait();
        });
    }
}

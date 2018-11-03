//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.31 at 02:24:35 PM CST 
//


package com.my.project.winscp.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.my.project.winscp.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Output_QNAME = new QName("http://winscp.net/schema/session/1.0", "output");
    private final static QName _Filename_QNAME = new QName("http://winscp.net/schema/session/1.0", "filename");
    private final static QName _Size_QNAME = new QName("http://winscp.net/schema/session/1.0", "size");
    private final static QName _Permissions_QNAME = new QName("http://winscp.net/schema/session/1.0", "permissions");
    private final static QName _Destination_QNAME = new QName("http://winscp.net/schema/session/1.0", "destination");
    private final static QName _Erroroutput_QNAME = new QName("http://winscp.net/schema/session/1.0", "erroroutput");
    private final static QName _Modification_QNAME = new QName("http://winscp.net/schema/session/1.0", "modification");
    private final static QName _Message_QNAME = new QName("http://winscp.net/schema/session/1.0", "message");
    private final static QName _Type_QNAME = new QName("http://winscp.net/schema/session/1.0", "type");
    private final static QName _Command_QNAME = new QName("http://winscp.net/schema/session/1.0", "command");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.my.project.winscp.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Upload }
     * 
     */
    public Upload createUpload() {
        return new Upload();
    }

    /**
     * Create an instance of {@link StringValueType }
     * 
     */
    public StringValueType createStringValueType() {
        return new StringValueType();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link Session }
     * 
     */
    public Session createSession() {
        return new Session();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link Failure }
     * 
     */
    public Failure createFailure() {
        return new Failure();
    }

    /**
     * Create an instance of {@link Download }
     * 
     */
    public Download createDownload() {
        return new Download();
    }

    /**
     * Create an instance of {@link Touch }
     * 
     */
    public Touch createTouch() {
        return new Touch();
    }

    /**
     * Create an instance of {@link DateTimeValueType }
     * 
     */
    public DateTimeValueType createDateTimeValueType() {
        return new DateTimeValueType();
    }

    /**
     * Create an instance of {@link Chmod }
     * 
     */
    public Chmod createChmod() {
        return new Chmod();
    }

    /**
     * Create an instance of {@link Mkdir }
     * 
     */
    public Mkdir createMkdir() {
        return new Mkdir();
    }

    /**
     * Create an instance of {@link Rm }
     * 
     */
    public Rm createRm() {
        return new Rm();
    }

    /**
     * Create an instance of {@link Mv }
     * 
     */
    public Mv createMv() {
        return new Mv();
    }

    /**
     * Create an instance of {@link Call }
     * 
     */
    public Call createCall() {
        return new Call();
    }

    /**
     * Create an instance of {@link Ls }
     * 
     */
    public Ls createLs() {
        return new Ls();
    }

    /**
     * Create an instance of {@link Files }
     * 
     */
    public Files createFiles() {
        return new Files();
    }

    /**
     * Create an instance of {@link File }
     * 
     */
    public File createFile() {
        return new File();
    }

    /**
     * Create an instance of {@link FileTypeValueType }
     * 
     */
    public FileTypeValueType createFileTypeValueType() {
        return new FileTypeValueType();
    }

    /**
     * Create an instance of {@link SizeValueType }
     * 
     */
    public SizeValueType createSizeValueType() {
        return new SizeValueType();
    }

    /**
     * Create an instance of {@link Stat }
     * 
     */
    public Stat createStat() {
        return new Stat();
    }

    /**
     * Create an instance of {@link Cp }
     * 
     */
    public Cp createCp() {
        return new Cp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "output")
    public JAXBElement<StringValueType> createOutput(StringValueType value) {
        return new JAXBElement<StringValueType>(_Output_QNAME, StringValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "filename")
    public JAXBElement<StringValueType> createFilename(StringValueType value) {
        return new JAXBElement<StringValueType>(_Filename_QNAME, StringValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SizeValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "size")
    public JAXBElement<SizeValueType> createSize(SizeValueType value) {
        return new JAXBElement<SizeValueType>(_Size_QNAME, SizeValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "permissions")
    public JAXBElement<StringValueType> createPermissions(StringValueType value) {
        return new JAXBElement<StringValueType>(_Permissions_QNAME, StringValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "destination")
    public JAXBElement<StringValueType> createDestination(StringValueType value) {
        return new JAXBElement<StringValueType>(_Destination_QNAME, StringValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "erroroutput")
    public JAXBElement<StringValueType> createErroroutput(StringValueType value) {
        return new JAXBElement<StringValueType>(_Erroroutput_QNAME, StringValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DateTimeValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "modification")
    public JAXBElement<DateTimeValueType> createModification(DateTimeValueType value) {
        return new JAXBElement<DateTimeValueType>(_Modification_QNAME, DateTimeValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "message")
    public JAXBElement<String> createMessage(String value) {
        return new JAXBElement<String>(_Message_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileTypeValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "type")
    public JAXBElement<FileTypeValueType> createType(FileTypeValueType value) {
        return new JAXBElement<FileTypeValueType>(_Type_QNAME, FileTypeValueType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StringValueType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://winscp.net/schema/session/1.0", name = "command")
    public JAXBElement<StringValueType> createCommand(StringValueType value) {
        return new JAXBElement<StringValueType>(_Command_QNAME, StringValueType.class, null, value);
    }

}
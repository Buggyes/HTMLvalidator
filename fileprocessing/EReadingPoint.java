package fileprocessing;
/**
 * <p>Usado para simplificar o código da leitura de documento HTML.
 * Todas as tags precisam passar por pelo menos <code>TagOpenning</code>, <code>Name</code>, e <code>TagClosure</code>, nesta sequência.
 * Como nem todas as tags precisam ter seus atributos declarados (exemplo: uma div), a passagem do valor <code>Atributes</code> é opcional.
 */
public enum EReadingPoint {
    TagOpenning,
    Name,
    Atributes,
    TagClosure
}

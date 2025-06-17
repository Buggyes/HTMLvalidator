package fileprocessing;
/**
 * <p>Usado para definir o resultado da leitura de uma linha.
 * <p><b>Ok</b> = Não foi encontrado nenhum erro na linha;
 * <p><b>ClosureAfterPreviousClosure</b> = Houve o fechamento de uma tag que não foi aberta;
 * <p><b>PrematureClosure</b> = Houve o fechamento prematuro de uma tag antes do fechamento de todas as tags 
 * que estão dentro da tag em questão (exemplo: uma div que fechou antes do span que estava contido dentro dela).
 */
public enum EReadResult {
    Ok,
    ClosureAfterPreviousClosure,
    PrematureClosure
}
